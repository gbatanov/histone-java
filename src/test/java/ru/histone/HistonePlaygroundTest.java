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
package ru.histone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * Test correct work of Histone public methods
 */
public class HistonePlaygroundTest {
    private Histone histone;
    private ObjectMapper jackson;

    @Before
    public void before() throws HistoneException, UnsupportedEncodingException {
        HistoneBuilder builder = new HistoneBuilder();
        histone = builder.build();
        jackson = new ObjectMapper();
    }

    @Test
    public void test() throws Exception {
        String input = "{{var f = 'world'}}{{var numbers = [1,2,3,4,5]}}\n{{for i in numbers}}\n{{for x in numbers}}{{var a = 'Hello'}}{{a}},{{f}} [{{self.index}} : {{self.last}}] {{self.qwe}}{{/for}}\\n{{for y in [numbers]}}{{var a = 'Hello'}}{{a}},{{f}} [{{i}} : {{self.last}}] {{self.index[0]}}{{/for}}\n{{/for}}";

        ArrayNode ast = histone.parseTemplateToAST(new StringReader(input));
        String astS = histone.evaluateAST(ast);
    }
}
