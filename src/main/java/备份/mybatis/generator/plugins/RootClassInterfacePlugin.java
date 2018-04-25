package 备份.mybatis.generator.plugins;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import 备份.mybatis.generator.external.PithyCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 基类处理插件
 * <p>
 * Client和Model基类处理
 *
 * @author Archx[archx@foxmail.com]
 * @date 2017/3/24
 */
public class RootClassInterfacePlugin extends PluginAdapter {

    // Model
    private Set<String> ignoreColumns;
    private String modelRootClass;
    private boolean useGenericId;

    // Mapper
    private String clientRootInterface;
    private String clientAnnotationClass;

    private boolean useExample;

    public RootClassInterfacePlugin() {
        ignoreColumns = new HashSet<>();
        useGenericId = false;
        useExample = false;
    }

    @Override
    public boolean validate(List<String> warnings) {

        // Model
        String modelRootClass = properties.getProperty("modelRootClass");
        if (StringUtility.stringHasValue(modelRootClass)) {
            this.modelRootClass = modelRootClass;
        }
        this.useGenericId = Boolean.valueOf(properties.getProperty("useGenericId"));
        // 过滤的字段
        String _ignoreColumns = properties.getProperty("ignoreColumns");
        if (StringUtility.stringHasValue(_ignoreColumns)) {
            Collections.addAll(ignoreColumns, _ignoreColumns.split(","));
        }

        // Mapper
        String clientRootInterface = properties.getProperty("clientRootInterface");
        if (StringUtility.stringHasValue(clientRootInterface)) {
            this.clientRootInterface = clientRootInterface;
        }
        String clientAnnotationClass = properties.getProperty("clientAnnotationClass");
        if (StringUtility.stringHasValue(clientAnnotationClass)) {
            this.clientAnnotationClass = clientAnnotationClass;
        }
        this.useExample = Boolean.valueOf(properties.getProperty("useExample"));

        return true;
    }

    /*------------------------------------
     |      M O D E L   E N T I T Y      |
     ====================================*/

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (StringUtility.stringHasValue(modelRootClass)) {
            addModelRootClass(topLevelClass, introspectedTable);
        }
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return !ignoreColumnCheck(introspectedColumn);
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return !ignoreColumnCheck(introspectedColumn);
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return !ignoreColumnCheck(introspectedColumn);
    }

    /**
     * 添加基类
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    private void addModelRootClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(new FullyQualifiedJavaType(modelRootClass));

        // 基础类
        StringBuilder builder = new StringBuilder(modelRootClass);

        IntrospectedColumn keyIntrospectedColumn = getPrimaryKeyIntrospectedColumn(introspectedTable);
        // 使用泛型ID
        if (useGenericId && keyIntrospectedColumn != null) {
            builder.append("<").append(keyIntrospectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName());
            builder.append(">");
        }

        topLevelClass.setSuperClass(new FullyQualifiedJavaType(builder.toString()));
    }

    /**
     * 过滤字段检查
     *
     * @param introspectedColumn
     * @return
     */
    private boolean ignoreColumnCheck(IntrospectedColumn introspectedColumn) {
        String columnName = introspectedColumn.getActualColumnName();
        for (String ignoreColumn : ignoreColumns) {
            if (columnName.toLowerCase().equals(ignoreColumn.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


    /*------------------------------------
     |     C L I E N T   M A P P E R     |
     ====================================*/

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        // 添加父级接口
        if (StringUtility.stringHasValue(clientRootInterface)) {
            addClientRootInterface(interfaze, introspectedTable);
        }

        // 添加注解
        if (StringUtility.stringHasValue(clientAnnotationClass)) {
            addClientAnnotationClass(interfaze);
        }

        // 注释
        addClientComment(interfaze, topLevelClass, introspectedTable);
        return true;
    }

    /**
     * 添加注解
     *
     * @param interfaze
     */
    private void addClientAnnotationClass(Interface interfaze) {
        FullyQualifiedJavaType annotation = new FullyQualifiedJavaType(clientAnnotationClass);
        interfaze.addImportedType(annotation);
        interfaze.addAnnotation("@" + annotation.getShortName());
    }

    /**
     * 添加父级接口
     *
     * @param interfaze
     * @param introspectedTable
     */
    private void addClientRootInterface(Interface interfaze, IntrospectedTable introspectedTable) {
        // 导入包
        interfaze.addImportedType(new FullyQualifiedJavaType(clientRootInterface));
        interfaze.addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));

        // 父级接口
        StringBuilder builder = new StringBuilder(clientRootInterface);
        // 泛型
        builder.append("<").append(introspectedTable.getBaseRecordType());

        // Example
        if (useExample) {
            String exampleType = introspectedTable.getExampleType();
            interfaze.addImportedType(new FullyQualifiedJavaType(exampleType));
            builder.append(",").append(exampleType);
        }

        // ID
        IntrospectedColumn idColumn = getPrimaryKeyIntrospectedColumn(introspectedTable);
        if (idColumn != null) {
            builder.append(",").append(idColumn.getFullyQualifiedJavaType().getFullyQualifiedName());
        }
        builder.append(">");

        interfaze.addSuperInterface(new FullyQualifiedJavaType(builder.toString()));
    }

    /**
     * 添加注释
     * <p>
     * 尝试用注释生成器来创建注释
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     */
    private void addClientComment(Interface interfaze, TopLevelClass topLevelClass,
                                  IntrospectedTable introspectedTable) {
        CommentGenerator commentGenerator = getContext().getCommentGenerator();
        if (commentGenerator instanceof PithyCommentGenerator) {
            ((PithyCommentGenerator) commentGenerator).addJavaElementComment(interfaze, interfaze.getType().getShortName());
        }
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
}
