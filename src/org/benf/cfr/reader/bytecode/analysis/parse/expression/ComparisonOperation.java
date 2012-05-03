package org.benf.cfr.reader.bytecode.analysis.parse.expression;

import org.benf.cfr.reader.bytecode.analysis.parse.Expression;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.LValueCollector;
import org.benf.cfr.reader.bytecode.analysis.parse.utils.SSAIdentifiers;

/**
 * Created by IntelliJ IDEA.
 * User: lee
 * Date: 16/03/2012
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public class ComparisonOperation implements ConditionalExpression {
    private Expression lhs;
    private Expression rhs;
    private final CompOp op;

    public ComparisonOperation(Expression lhs, Expression rhs, CompOp op) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    @Override
    public String toString() {
        return "(" + lhs.toString() + " " + op.getShowAs() + " " + rhs.toString() + ")";
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public Expression replaceSingleUsageLValues(LValueCollector lValueCollector, SSAIdentifiers ssaIdentifiers) {
        lhs = lhs.replaceSingleUsageLValues(lValueCollector, ssaIdentifiers);
        rhs = rhs.replaceSingleUsageLValues(lValueCollector, ssaIdentifiers);
        return this;
    }

    @Override
    public ConditionalExpression getNegatedExpression() {
        return new ComparisonOperation(lhs, rhs, op.getInverted());
    }
}
