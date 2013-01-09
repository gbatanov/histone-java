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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import ru.histone.HistoneException;
import ru.histone.evaluator.Evaluator;
import ru.histone.evaluator.EvaluatorException;
import ru.histone.evaluator.nodes.Node;
import ru.histone.evaluator.nodes.NodeFactory;
import ru.histone.utils.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: sazonovkirill@gmail.com
 * Date: 25.12.12
 */
public class ConstantFolding extends BaseOptimization {
    private final Evaluator evaluator;

    public ConstantFolding(NodeFactory nodeFactory, Evaluator evaluator) {
        super(nodeFactory);
        this.evaluator = evaluator;
    }

    public ArrayNode foldConstants(ArrayNode ast) throws HistoneException {
        return process(ast);
    }

    @Override
    public void pushContext() {
        // There is no context to push in this optimizer
    }

    @Override
    public void popContext() {
        // There is no context to push in this optimizer
    }

    protected JsonNode processOperationOverArguments(ArrayNode ast) throws HistoneException {
        Assert.notNull(ast);
        Assert.notNull(ast.size() > 1);
        Assert.isTrue(ast.get(0).isNumber());
        for (int i = 1; i < ast.size(); i++) Assert.isTrue(ast.get(i).isArray());
        Assert.isTrue(getOperationsOverArguments().contains(ast.get(0).asInt()));

        int operationType = ast.get(0).asInt();

        final List<ArrayNode> processedArguments = new ArrayList<ArrayNode>();
        for (int i = 1; i < ast.size(); i++) {
            JsonNode processedArg = processAstNode(ast.get(i));
            Assert.isTrue(processedArg.isArray());
            processedArguments.add((ArrayNode) processedArg);
        }

        if (isConstants(processedArguments)) {
            Node node = evaluate(operationType, processedArguments);
            return node2Ast(node);
        } else {
            return ast(operationType, processedArguments);
        }
    }

    private Node evaluate(int operationType, Collection<? extends ArrayNode> arguments) throws EvaluatorException {
        return evaluator.evaluate(ast(operationType, arguments));
    }

    private Node evaluate(int operationType, ArrayNode... arguments) throws EvaluatorException {
        return evaluator.evaluate(ast(operationType, arguments));
    }
}
