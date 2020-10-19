package com.szy.config;

import com.szy.pojo.DataBaseConfiguration;
import com.szy.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.security.auth.login.Configuration;
import java.io.InputStream;
import java.util.List;

/**
 * @Author Suzy
 * @Date 2020-10-18
 */
public class XMLMapperBuilder {

    private DataBaseConfiguration configuration;

    public XMLMapperBuilder(DataBaseConfiguration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream resourceAsStream) {
        try {
            Document document = new SAXReader().read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> selectElement = rootElement.selectNodes("//select");
            selectElement.forEach(element -> {
                String id = element.attributeValue("id");
                String resultType = element.attributeValue("resultType");
                String paramterType = element.attributeValue("paramterType");
                String textTrim = element.getTextTrim();
                MappedStatement mappedStatement = new MappedStatement();
                mappedStatement.setId(id);
                mappedStatement.setParamterType(paramterType);
                mappedStatement.setResultType(resultType);
                mappedStatement.setSql(textTrim);

                // sql唯一标识：namespace.id
                configuration.getMappedStatementMap().put(rootElement.attributeValue("namespace") + "." + id, mappedStatement);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
