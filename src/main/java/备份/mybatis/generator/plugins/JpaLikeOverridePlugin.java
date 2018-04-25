package 备份.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * JpaLikeOverridePlugin
 *
 * @author Archx[archx@foxmail.com]
 * @date 2017/3/24
 */
public class JpaLikeOverridePlugin extends PluginAdapter {

    private boolean useColumnAlias; //是否使用别名
    private boolean useExample;  //是否使用Example
    private boolean cleanClient; //是否清理Mapper接口中的方法
    private boolean pageable;    //是否使用分页插件

    public JpaLikeOverridePlugin() {
        this.useColumnAlias = false;
        this.useExample = false;
        this.cleanClient = false;
        this.pageable = false;
    }

    @Override
    public boolean validate(List<String> warnings) {
        String useExample = properties.getProperty("useExample");
        if (StringUtility.stringHasValue(useExample)) {
            this.useExample = Boolean.valueOf(useExample);
        }

        String useColumnAlias = properties.getProperty("useColumnAlias");
        if (StringUtility.stringHasValue(useColumnAlias)) {
            this.useColumnAlias = Boolean.valueOf(useColumnAlias);
        }

        String cleanClient = properties.getProperty("cleanClient");
        if (StringUtility.stringHasValue(cleanClient)) {
            this.cleanClient = Boolean.valueOf(cleanClient);
        }

        String pageable = properties.getProperty("pageable");
        if (StringUtility.stringHasValue(pageable)) {
            this.pageable = Boolean.valueOf(pageable);
        }

        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        resetXmlAttributes(introspectedTable);
    }


    /*--------------------------------------------
    |          C O L U M N   L I S T            |
    ============================================*/

    @Override
    public boolean sqlMapBaseColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        addSqlBaseColumn(element, introspectedTable);
        return true;
    }

    @Override
    public boolean sqlMapBlobColumnListElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        addSqlBlobColumn(element, introspectedTable);
        return true;
    }

    /*--------------------------------------------
    |       D I S A B L E   M T H O D S         |
    ============================================*/

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                       IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                        IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                           IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze,
                                               IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, TopLevelClass topLevelClass,
                                               IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                 IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                           IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                 IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                 IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                    IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
                                                                       IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                                       IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass,
                                                        IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element,
                                                                        IntrospectedTable introspectedTable) {
        return false;
    }

    /*--------------------------------------------
    |         E N A B L   M T H O D S           |
    ============================================*/

    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze,
                                                       IntrospectedTable introspectedTable) {
        return useExample && !cleanClient;
    }

    @Override
    public boolean clientDeleteByExampleMethodGenerated(Method method, Interface interfaze,
                                                        IntrospectedTable introspectedTable) {
        return useExample && !cleanClient;
    }

    @Override
    public boolean clientUpdateByExampleSelectiveMethodGenerated(Method method, Interface interfaze,
                                                                 IntrospectedTable introspectedTable) {
        return useExample && !cleanClient;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return useExample;
    }

    @Override
    public boolean sqlMapCountByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return useExample;
    }

    @Override
    public boolean sqlMapDeleteByExampleElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return useExample;
    }

    @Override
    public boolean sqlMapExampleWhereClauseElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return useExample;
    }

    @Override
    public boolean sqlMapUpdateByExampleSelectiveElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        return useExample;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
                                                           IntrospectedTable introspectedTable) {
        return !cleanClient;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze,
                                                           IntrospectedTable introspectedTable) {
        return !cleanClient;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze,
                                                                    IntrospectedTable introspectedTable) {
        return !cleanClient;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze,
                                                        IntrospectedTable introspectedTable) {
        return !cleanClient;
    }

    /*--------------------------------------------
    |      M T H O D S   G E N E R A T E        |
    ============================================*/

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,
                                   IntrospectedTable introspectedTable) {
        if (!cleanClient) {
            this.addFindAllMethod(interfaze, introspectedTable, false);
            if (pageable)
                this.addFindAllMethod(interfaze, introspectedTable, true);

            if (useExample) {
                this.addFindByExampleMethod(interfaze, introspectedTable, false);
                if (pageable)
                    this.addFindByExampleMethod(interfaze, introspectedTable, true);
            }
        }
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        this.addFindAllSqlMap(document, introspectedTable);
        if (useExample) {
            this.addFindByExampleSqlMap(document, introspectedTable);
        }
        return true;
    }

    /**
     * 重设XML属性
     *
     * @param introspectedTable
     */
    private void resetXmlAttributes(IntrospectedTable introspectedTable) {

        // JPA Style
        introspectedTable.setSelectByPrimaryKeyStatementId("findOne");
        introspectedTable.setInsertSelectiveStatementId("save");
        introspectedTable.setUpdateByPrimaryKeySelectiveStatementId("update");
        introspectedTable.setDeleteByPrimaryKeyStatementId("delete");
        introspectedTable.setUpdateByExampleSelectiveStatementId("updateByExample");

        // Sql Id
        String entityName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();

        introspectedTable.setBaseResultMapId(entityName + "BaseResultMap");
        introspectedTable.setResultMapWithBLOBsId(entityName + "ResultMapWithBLOBs");
        introspectedTable.setExampleWhereClauseId(entityName + "WhereClause");
        introspectedTable.setBaseColumnListId(entityName + "BaseColumns");
        introspectedTable.setBlobColumnListId(entityName + "BlobColumns");
        introspectedTable.setMyBatis3UpdateByExampleWhereClauseId(entityName + "UpdateClause");
    }

    /**
     * 添加sql字段
     *
     * @param element
     * @param introspectedTable
     */
    private void addSqlBaseColumn(XmlElement element, IntrospectedTable introspectedTable) {
        // 清空重写
        element.getElements().clear();
        context.getCommentGenerator().addComment(element);

        StringBuilder sb = new StringBuilder();

        Iterator<IntrospectedColumn> iter = introspectedTable.getNonBLOBColumns().iterator();
        buildSqlColumn(element, sb, iter);

        if (sb.length() > 0) {
            element.addElement(new TextElement(sb.toString()));
        }
    }

    /**
     * 添加sql字段
     *
     * @param element
     * @param introspectedTable
     */
    private void addSqlBlobColumn(XmlElement element, IntrospectedTable introspectedTable) {
        // 清空重写
        element.getElements().clear();
        context.getCommentGenerator().addComment(element);

        StringBuilder sb = new StringBuilder();

        Iterator<IntrospectedColumn> iter = introspectedTable.getBLOBColumns().iterator();
        buildSqlColumn(element, sb, iter);

        if (sb.length() > 0) {
            element.addElement(new TextElement(sb.toString()));
        }
    }

    /**
     * 构建SQL字段列表
     *
     * @param element
     * @param sb
     * @param iter
     */
    private void buildSqlColumn(XmlElement element, StringBuilder sb, Iterator<IntrospectedColumn> iter) {
        while (iter.hasNext()) {
            String column = MyBatis3FormattingUtilities.getSelectListPhrase(iter.next());
            sb.append(column);

            if (useColumnAlias)
                sb.append(getColumnIndentSpaces(column)).append("    as ").append(column);

            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }
            element.addElement(new TextElement(sb.toString()));
            sb.setLength(0);
        }
    }

    /**
     * 获取主键列
     *
     * @param column
     * @return
     */
    private String getColumnIndentSpaces(String column) {
        int length = column.length();
        if (length > 20) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (length++ < 20) {
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 添加findAll方法
     *
     * @param interfaze
     * @param introspectedTable
     * @param pageable
     */
    private void addFindAllMethod(Interface interfaze, IntrospectedTable introspectedTable, boolean pageable) {
        TreeSet<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        // 方法名称
        Method method = new Method("findAll");
        // 修饰符
        method.setVisibility(JavaVisibility.PUBLIC);
        // 返回类型
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);
        // 添加参数
        method.addParameter(new Parameter(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()), "record"));
        // 分页参数
        if (pageable) {
            FullyQualifiedJavaType pagination = new FullyQualifiedJavaType("org.apache.ibatis.session.RowBounds");
            method.addParameter(new Parameter(pagination, "bounds"));
            importedTypes.add(pagination);
        }
        // 添加注释
        getContext().getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        // 添加方法
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    /**
     * 添加 findByExample 方法
     *
     * @param interfaze
     * @param introspectedTable
     * @param pageable
     */
    private void addFindByExampleMethod(Interface interfaze, IntrospectedTable introspectedTable, boolean pageable) {
        TreeSet<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());

        // 方法名称
        Method method = new Method("findByExample");
        // 修饰符
        method.setVisibility(JavaVisibility.PUBLIC);
        // 返回类型
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());

        importedTypes.add(listType);
        returnType.addTypeArgument(listType);

        method.setReturnType(returnType);

        // 添加参数
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        importedTypes.add(type);
        method.addParameter(new Parameter(type, "example"));
        // 分页参数
        if (pageable) {
            FullyQualifiedJavaType pagination = new FullyQualifiedJavaType("org.apache.ibatis.session.RowBounds");
            method.addParameter(new Parameter(pagination, "bounds"));
            importedTypes.add(pagination);
        }
        // 添加注释
        getContext().getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        // 添加方法
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    /**
     * 添加FindAll SQL Map
     *
     * @param document
     * @param introspectedTable
     */
    private void addFindAllSqlMap(Document document, IntrospectedTable introspectedTable) {
        XmlElement parentElement = document.getRootElement();
        // 查询标签
        XmlElement findAll = new XmlElement("select");
        findAll.addAttribute(new Attribute("id", "findAll"));
        findAll.addAttribute(new Attribute("parameterType", introspectedTable.getBaseRecordType()));
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            findAll.addAttribute(new Attribute("resultMap", introspectedTable.getResultMapWithBLOBsId()));
        } else {
            findAll.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId()));
        }

        // 添加注释
        getContext().getCommentGenerator().addComment(findAll);

        // 构建语句
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        findAll.addElement(new TextElement(sb.toString()));

        // include
        findAll.addElement(this.getBaseColumnListElement(introspectedTable));
        if (introspectedTable.hasBLOBColumns()) {
            findAll.addElement(new TextElement(","));
            findAll.addElement(this.getBlobColumnListElement(introspectedTable));
        }

        sb.setLength(0);
        sb.append("from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        findAll.addElement(new TextElement(sb.toString()));

        // <where>
        XmlElement where = new XmlElement("where");
        findAll.addElement(where);

        Iterator i$ = introspectedTable.getAllColumns().iterator();

        while (i$.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn) i$.next();
            XmlElement isNotNullElement = new XmlElement("if");

            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            // 加入<where>
            where.addElement(isNotNullElement);

            sb.setLength(0);
            sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

        parentElement.addElement(findAll);
    }

    /**
     * 添加 findByExample
     *
     * @param document
     * @param introspectedTable
     */
    private void addFindByExampleSqlMap(Document document, IntrospectedTable introspectedTable) {

        XmlElement parentElement = document.getRootElement();

        String fqjt = introspectedTable.getExampleType();

        XmlElement findByExample = new XmlElement("select"); //$NON-NLS-1$
        findByExample.addAttribute(new Attribute("id", "findByExample")); //$NON-NLS-1$

        String resultMap = introspectedTable.getBaseResultMapId();
        if (introspectedTable.hasBLOBColumns()) {
            resultMap = introspectedTable.getResultMapWithBLOBsId();
        }
        findByExample.addAttribute(new Attribute("resultMap", resultMap)); //$NON-NLS-1$
        findByExample.addAttribute(new Attribute("parameterType", fqjt)); //$NON-NLS-1$

        getContext().getCommentGenerator().addComment(findByExample);

        findByExample.addElement(new TextElement("select")); //$NON-NLS-1$
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "distinct")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("distinct")); //$NON-NLS-1$
        findByExample.addElement(ifElement);

        StringBuilder sb = new StringBuilder();
        if (StringUtility.stringHasValue(introspectedTable.getSelectByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByExampleQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
            findByExample.addElement(new TextElement(sb.toString()));
        }

        findByExample.addElement(getBaseColumnListElement(introspectedTable));

        if (introspectedTable.hasBLOBColumns()) {
            findByExample.addElement(new TextElement(",")); //$NON-NLS-1$
            findByExample.addElement(getBlobColumnListElement(introspectedTable));
        }

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        findByExample.addElement(new TextElement(sb.toString()));
        findByExample.addElement(getExampleIncludeElement(introspectedTable));

        ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "orderByClause != null")); //$NON-NLS-1$ //$NON-NLS-2$
        ifElement.addElement(new TextElement("order by ${orderByClause}")); //$NON-NLS-1$
        findByExample.addElement(ifElement);

        parentElement.addElement(findByExample);
    }

    /**
     * 获取基础列xml引用
     *
     * @param introspectedTable
     * @return
     */
    private XmlElement getBaseColumnListElement(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", introspectedTable.getBaseColumnListId()));
        return answer;
    }

    /**
     * 获取Blob列xml引用
     *
     * @param introspectedTable
     * @return
     */
    private XmlElement getBlobColumnListElement(IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("include");
        answer.addAttribute(new Attribute("refid", introspectedTable.getBlobColumnListId()));
        return answer;
    }

    /**
     * 获取Where Clause 应用
     *
     * @param introspectedTable
     * @return
     */
    protected XmlElement getExampleIncludeElement(IntrospectedTable introspectedTable) {
        XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
        ifElement.addAttribute(new Attribute("test", "_parameter != null")); //$NON-NLS-1$ //$NON-NLS-2$

        XmlElement includeElement = new XmlElement("include"); //$NON-NLS-1$
        includeElement.addAttribute(new Attribute("refid", //$NON-NLS-1$
                                                  introspectedTable.getExampleWhereClauseId()));
        ifElement.addElement(includeElement);

        return ifElement;
    }
}
