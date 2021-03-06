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
package ru.histone.evaluator.functions.global;

import ru.histone.evaluator.nodes.Node;
import ru.histone.evaluator.nodes.NodeFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetCurrentDate extends GlobalFunction {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    protected GetCurrentDate(NodeFactory nodeFactory) {
        super(nodeFactory);
    }

    @Override
    public String getName() {
        return "getCurrentDate";
    }

    @Override
    public Node execute(Node... args) throws GlobalFunctionExecutionException {
        return getNodeFactory().string(sdf.format(Calendar.getInstance().getTime()));
    }
}
