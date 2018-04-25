package 备份.mybatis.generator.external;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * 简洁注释生成器
 * <p>
 * 使用方式: <br/>
 * 在generatorConfig.xml配置文件的&lt;context&gt;标签中添加
 * <blockquote><pre>
 * &lt;commentGenerator type="PithyCommentGenerator"&gt;
 *      &lt;property name="author" value="Archx[archx@foxmail.com]"/&gt;
 *      &lt;property name="datePattern" value="yyyy/MM/dd HHmm"/&gt;
 * &lt;/commentGenerator&gt;
 * </blockquote></pre>
 *
 * @author Archx[archx@foxmail.com]
 * @date 2017/3/24
 */
public class PithyCommentGenerator implements CommentGenerator {

    private Properties properties;
    private DateFormat dateFormat;
    private String author;

    public PithyCommentGenerator() {
        super();
        properties = new Properties();
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        String author = properties.getProperty("author");
        if (StringUtility.stringHasValue(author)) {
            this.author = author;
        } else {
            this.author = System.getProperty("user.name");
        }

        String datePattern = properties.getProperty("datePattern");
        if (!StringUtility.stringHasValue(datePattern)) {
            datePattern = "yyyy/MM/dd";
        }
        this.dateFormat = new SimpleDateFormat(datePattern);
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String comment = getIntrospectedColumnComment(introspectedTable, introspectedColumn);

        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * " + comment);
        field.addJavaDocLine(" */");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        topLevelClass.addJavaDocLine(" * ");
        topLevelClass.addJavaDocLine(" * @author " + this.author);
        topLevelClass.addJavaDocLine(" * @date " + dateFormat.format(Calendar.getInstance().getTime()));
        topLevelClass.addJavaDocLine(" */");
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
                                 IntrospectedColumn introspectedColumn) {
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }

    @Override
    public void addComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!-- " + MergeConstants.NEW_ELEMENT_TAG + " -->"));
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
    }

    /**
     * 添加Java Element 注释
     *
     * @param interfaze
     * @param name
     */
    public void addJavaElementComment(JavaElement interfaze, String name) {
        // 添加注释
        interfaze.addJavaDocLine("/**"); //$NON-NLS-1$
        interfaze.addJavaDocLine(" * " + name);
        interfaze.addJavaDocLine(" * ");
        interfaze.addJavaDocLine(" * @author " + this.author);
        interfaze.addJavaDocLine(" * @date " + dateFormat.format(Calendar.getInstance().getTime()));
        interfaze.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    /**
     * 添加共通注释
     *
     * @param element
     * @param introspectedTable
     */
    protected void addJavaElementCommonComment(JavaElement element, IntrospectedTable introspectedTable) {
        element.addJavaDocLine("/**");
        element.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
        element.addJavaDocLine(" */");
    }

    /**
     * 获取列注释
     *
     * @param introspectedTable
     * @param introspectedColumn
     * @return
     */
    private String getIntrospectedColumnComment(IntrospectedTable introspectedTable,
                                                IntrospectedColumn introspectedColumn) {
        String comment = introspectedColumn.getRemarks();

        if (!StringUtility.stringHasValue(comment)) {
            comment = introspectedTable.getFullyQualifiedTable() + "." + introspectedColumn.getActualColumnName();
        }
        return comment;
    }
}
