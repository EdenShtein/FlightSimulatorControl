package expressions;

public class Or extends BinaryExpression {
    public Or(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double calculate() {
        if((left.calculate()+right.calculate())>0)
            return 1;
        return 0;
    }
}
