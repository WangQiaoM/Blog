package 备份.mybatis.generator.plugins;

import org.mybatis.generator.api.*;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;
import 备份.mybatis.generator.external.PithyCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisSpringServicePlugin
 *
 * @author Archx[archx@foxmail.com]
 * @date 2016/10/11
 */
public class MyBatisSpringServicePlugin extends PluginAdapter {

    private String project;
    private JavaFormatter javaFormatter;

    // Packages
    private String servicePack;
    private String serviceImplPack;
    private String domainPack;

    // Spring 注解
    private FullyQualifiedJavaType autowiredAnnotation;
    private FullyQualifiedJavaType serviceAnnotation;

    // Types
    private FullyQualifiedJavaType serviceInterfaceType;
    private FullyQualifiedJavaType serviceImplementType;
    private FullyQualifiedJavaType domainType;
    private FullyQualifiedJavaType mapperType;

    // Root Types
    private String rootInterface;
    private String rootImplement;

    // Switch
    private boolean overrideGetMapperMethod; // 重写getMapper方法
    private boolean switchOn; // 应用插件开关
    private boolean useExample; // 使用Example

    public MyBatisSpringServicePlugin() {
        super();
        this.overrideGetMapperMethod = true;
        this.switchOn = true;
        this.useExample = false;
    }

    @Override
    public boolean validate(List<String> list) {
        javaFormatter = new DefaultJavaFormatter();
        javaFormatter.setContext(context);

        // 注解
        autowiredAnnotation = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");
        serviceAnnotation = new FullyQualifiedJavaType("org.springframework.stereotype.Service");

        // 实体包名
        domainPack = context.getJavaModelGeneratorConfiguration().getTargetPackage();

        this.project = properties.getProperty("targetProject");

        this.servicePack = properties.getProperty("targetPackage");
        this.serviceImplPack = properties.getProperty("implementationPackage");

        // 基础Service接口和实现
        this.rootInterface = properties.getProperty("rootInterface");
        this.rootImplement = properties.getProperty("rootImplement");

        String switchOn = properties.getProperty("switchOn");
        if (StringUtility.stringHasValue(switchOn)) {
            this.switchOn = Boolean.valueOf(switchOn);
        }
        String overrideGetMapperMethod = properties.getProperty("overrideGetMapperMethod");
        if (StringUtility.stringHasValue(overrideGetMapperMethod)) {
            this.overrideGetMapperMethod = Boolean.valueOf(overrideGetMapperMethod);
        }

        String useExample = properties.getProperty("useExample");
        if (StringUtility.stringHasValue(useExample)) {
            this.useExample = Boolean.valueOf(useExample);
        }
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {

        if (!switchOn) {
            return super.contextGenerateAdditionalJavaFiles(introspectedTable);
        }

        List<GeneratedJavaFile> files = new ArrayList<GeneratedJavaFile>();
        String recordType = introspectedTable.getBaseRecordType();
        String domainName = recordType.replaceAll(domainPack + ".", "");

        serviceInterfaceType = new FullyQualifiedJavaType(servicePack + "." + domainName + "Service");
        serviceImplementType = new FullyQualifiedJavaType(serviceImplPack + "." + domainName + "ServiceImpl");
        domainType = new FullyQualifiedJavaType(domainPack + "." + domainName);
        mapperType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());

        Interface service = new Interface(serviceInterfaceType);
        TopLevelClass serviceImpl = new TopLevelClass(serviceImplementType);

        // 类注释
        CommentGenerator commentGenerator = getContext().getCommentGenerator();
        if (commentGenerator instanceof PithyCommentGenerator) {
            PithyCommentGenerator commenter = (PithyCommentGenerator) commentGenerator;
            commenter.addJavaElementComment(service, service.getType().getShortName());
            commenter.addJavaElementComment(serviceImpl, serviceImpl.getType().getShortName());
        }

        // 导入类
        addImportType(service, serviceImpl);

        // 接口
        GeneratedJavaFile serviceFile = buildServiceFile(service, introspectedTable);
        files.add(serviceFile);

        // 实现
        GeneratedJavaFile serviceImplFile = buildServiceImplementFile(serviceImpl, introspectedTable);
        files.add(serviceImplFile);

        return files;
    }

    protected void addImportType(Interface service, TopLevelClass serviceImpl) {

        service.addImportedType(domainType);

        serviceImpl.addImportedType(domainType);
        serviceImpl.addImportedType(mapperType);
        serviceImpl.addImportedType(serviceInterfaceType);

        serviceImpl.addImportedType(serviceAnnotation);
        serviceImpl.addImportedType(autowiredAnnotation);
    }

    /**
     * 构建接口文件
     *
     * @param service
     * @param introspectedTable
     * @return
     */
    protected GeneratedJavaFile buildServiceFile(Interface service, IntrospectedTable introspectedTable) {
        service.setVisibility(JavaVisibility.PUBLIC);

        // 继承基础类
        if (StringUtility.stringHasValue(rootInterface)) {
            StringBuilder builder = new StringBuilder(rootInterface);

            service.addImportedType(new FullyQualifiedJavaType(rootInterface));

            // 泛型
            builder.append("<").append(introspectedTable.getBaseRecordType());

            if (useExample) {
                String exampleType = introspectedTable.getExampleType();
                service.addImportedType(new FullyQualifiedJavaType(exampleType));
                builder.append(",").append(exampleType);
            }

            // ID
            IntrospectedColumn keyColumn = getPrimaryKeyIntrospectedColumn(introspectedTable);
            if (keyColumn != null) {
                builder.append(",").append(keyColumn.getFullyQualifiedJavaType().getFullyQualifiedName());
            }
            builder.append(">");
            service.addSuperInterface(new FullyQualifiedJavaType(builder.toString()));
        }
        return new GeneratedJavaFile(service, project, javaFormatter);
    }

    /**
     * 构建接口实现文件
     *
     * @param serviceImpl
     * @param introspectedTable
     * @return
     */
    protected GeneratedJavaFile buildServiceImplementFile(TopLevelClass serviceImpl,
                                                          IntrospectedTable introspectedTable) {
        serviceImpl.setVisibility(JavaVisibility.PUBLIC);

        // 设置实现的接口
        serviceImpl.addSuperInterface(serviceInterfaceType);
        // Spring 注解
        serviceImpl.addAnnotation("@Service");

        // Mapper
        addMapper(serviceImpl);

        // 基础类
        if (StringUtility.stringHasValue(rootImplement)) {
            serviceImpl.addImportedType(new FullyQualifiedJavaType(rootImplement));

            StringBuilder builder = new StringBuilder(rootImplement);
            // 泛型
            builder.append("<").append(introspectedTable.getBaseRecordType());

            if (useExample) {
                String exampleType = introspectedTable.getExampleType();
                serviceImpl.addImportedType(new FullyQualifiedJavaType(exampleType));
                builder.append(",").append(exampleType);
            }

            // ID
            IntrospectedColumn keyColumn = getPrimaryKeyIntrospectedColumn(introspectedTable);
            if (keyColumn != null) {
                builder.append(",").append(keyColumn.getFullyQualifiedJavaType().getFullyQualifiedName());
            }
            builder.append(">");
            serviceImpl.setSuperClass(new FullyQualifiedJavaType(builder.toString()));
        }

        if (overrideGetMapperMethod) {
            addGetMapperMethod(serviceImpl);
        }

        return new GeneratedJavaFile(serviceImpl, project, javaFormatter);
    }

    /**
     * 获取主键列
     *
     * @param introspectedTable
     * @return
     */
    private IntrospectedColumn getPrimaryKeyIntrospectedColumn(IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        if (primaryKeyColumns.isEmpty()) {
            return null;
        }
        return primaryKeyColumns.get(0);
    }

    /**
     * 添加 Mapper client
     *
     * @param serviceImpl
     */
    private void addMapper(TopLevelClass serviceImpl) {
        Field field = new Field();
        field.setName("mapper");
        field.setType(mapperType);
        field.setVisibility(JavaVisibility.PRIVATE);

        // field.addAnnotation("@Autowired"); 不建议直接在字段上使用自动装配注解
        serviceImpl.addField(field);

        // 实现set方法 并添加自动装配注解
        Method method = new Method();

        method.setName("setMapper");
        method.setVisibility(JavaVisibility.PUBLIC);

        method.addParameter(new Parameter(mapperType, "mapper"));
        method.addBodyLine("this.mapper = mapper;");
        // Spring 注解
        method.addAnnotation("@Autowired");
        serviceImpl.addMethod(method);
    }

    /**
     * 添加 Mapper client 获取方法
     *
     * @param serviceImpl
     */
    private void addGetMapperMethod(TopLevelClass serviceImpl) {
        Method method = new Method();
        method.setName("getMapper");
        method.setVisibility(JavaVisibility.PUBLIC);

        method.addBodyLine("return this.mapper;");

        method.setReturnType(mapperType);

        method.addAnnotation("@Override");
        serviceImpl.addMethod(method);

    }
}
