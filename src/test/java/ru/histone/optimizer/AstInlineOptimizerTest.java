/**
 *    Copyright 2012 MegaFon
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package ru.histone.optimizer;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.histone.Histone;
import ru.histone.HistoneBuilder;
import ru.histone.HistoneException;
import ru.histone.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;

/**
 * User: sazonovkirill@gmail.com
 * Date: 24.12.12
 */
@Ignore("This test is not correct. We need to investigate it more precisely")
public class AstInlineOptimizerTest {
    private Histone histone;

    @Before
    public void init() throws HistoneException {
        HistoneBuilder histoneBuilder = new HistoneBuilder();
        histone = histoneBuilder.build();
    }

    @Test
    public void inlineIf() throws HistoneException, IOException {
        String input = input("inline_if.tpl");
        ArrayNode ast = histone.parseTemplateToAST(new StringReader(input));
        ast = histone.optimizeAST(ast);

        assertTrue(ast.get(1).get(0).asInt() > 0);
    }

    @Test
    public void inlineFor() throws HistoneException, IOException {
        String input = input("inline_for.tpl");
        ArrayNode ast = histone.parseTemplateToAST(new StringReader(input));
        ast = histone.optimizeAST(ast);

        assertTrue(ast.get(1).get(0).asInt() > 0);
    }

    @Test
    public void inlineVar() throws HistoneException, IOException {
        String input = input("inline_var.tpl");
        ArrayNode ast = histone.parseTemplateToAST(new StringReader(input));
        ast = histone.optimizeAST(ast);

        assertTrue(ast.get(1).get(0).asInt() > 0);
    }

    @Test
    public void inlineStatements() throws HistoneException, IOException {
        String input = input("inline_statements.tpl");
        ArrayNode ast = histone.parseTemplateToAST(new StringReader(input));
        ast = histone.optimizeAST(ast);

        assertTrue(ast.get(1).get(0).asInt() > 0);
    }

    @Test
    @Ignore("Some questions. Why it doesnt work?")
    public void inlineSelector() throws HistoneException, IOException {
        String input = input("inline_selector.tpl");
        ArrayNode ast = histone.parseTemplateToAST(new StringReader(input));
        ast = histone.optimizeAST(ast);

        assertTrue(ast.get(1).get(0).asInt() > 0);
    }

    @Test
    public void inlineCall() throws HistoneException, IOException {
        String input = input("inline_call.tpl");
        ArrayNode ast = histone.parseTemplateToAST(new StringReader(input));
        ast = histone.optimizeAST(ast);

        assertTrue(ast.get(1).get(0).asInt() > 0);
    }

    @Test
    public void inlineMacro() throws HistoneException, IOException {
        String input = input("inline_macro.tpl");
        ArrayNode ast = histone.parseTemplateToAST(new StringReader(input));
        ast = histone.optimizeAST(ast);

        assertTrue(ast.get(1).get(0).asInt() > 0);
    }

    private String input(String filename) throws IOException {
        StringWriter sw = new StringWriter();
        InputStream is = getClass().getClassLoader().getResourceAsStream("optimizer/inline/" + filename);
        IOUtils.copy(is, sw);
        return sw.toString();
    }
}
