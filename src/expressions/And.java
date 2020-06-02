package expressions;


public class And extends BinaryExpression {

    public And(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double calculate() {
        if((left.calculate()+right.calculate())==2)
            return 1;
        return 0;
    }
}
