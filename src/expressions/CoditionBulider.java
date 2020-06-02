package expressions;


import java.util.LinkedList;

import java.util.Stack;


public class CoditionBulider {
    public static double calcc(String expression){
        StringBuilder deli=new StringBuilder(expression);
        Number num1,num2;
        Expression exp=null;
        int i = deli.indexOf("&");
        int j = deli.indexOf("|");
        if(i>0||j>0) {
            if((j<i&&j!=-1)||i==-1)
                i=j;
            num1 = new Number(ShuntingYardPredicate.calc(deli.substring(0, i)));
            num2 = new Number(CoditionBulider.calc(deli.substring(i + 2)));
            switch (deli.charAt(i)) {
                case '|':
                    exp = new Or(num1, num2);
                    break;
                case '&':
                    exp = new And(num1, num2);
                    break;
            }
            return exp.calculate();

        }
        return ShuntingYardPredicate.calc(expression);
    }

    public static double calc(String expression){
        LinkedList<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        String token = "";
        while(expression.contains("&")||expression.contains("|")){
            int i = expression.indexOf("&");
            int j = expression.indexOf("|");
            if((j<i&&j!=-1)||i==-1)
                i=j;
            token=ShuntingYardPredicate.calc(expression.substring(0, i))+"";
            queue.addFirst(token);
            token=expression.charAt(i)+"";
            switch (token) {

                case "|":
                    while (!stack.isEmpty() )
                        queue.addFirst(stack.pop());
                    stack.push(token);
                    break;
                case "&":
                    while (!stack.isEmpty() && (stack.peek().equals("&") ))
                        queue.addFirst(stack.pop());
                    stack.push(token);
                    break;
                default: // Always a number
                    queue.addFirst(token);
                    break;
            }
            expression=expression.substring(i + 2);
        }
        token=ShuntingYardPredicate.calc(expression)+"";
        queue.addFirst(token);
        while (!stack.isEmpty())
            queue.addFirst(stack.pop());
        Expression finalExpression = buildExpression(queue);
        double answer = finalExpression.calculate();
        return Double.parseDouble(String.format("%.3f", answer));
    }
    private static Expression buildExpression(LinkedList<String> queue) {
        Expression returnedExpression = null;
        Expression right = null;
        Expression left = null;
        String currentExpression = queue.removeFirst();
        if (currentExpression.equals("|") || currentExpression.equals("&") ) {
            right = buildExpression(queue);
            left = buildExpression(queue);
        }
        switch (currentExpression) {
            case "|":
                returnedExpression = new Or(left, right);
                break;
            case "&":
                returnedExpression = new And(left, right);
                break;
            default:
                returnedExpression = new Number(
                        Double.parseDouble(String.format("%.2f", Double.parseDouble(currentExpression))));
                break;
        }

        return returnedExpression;
    }
}
