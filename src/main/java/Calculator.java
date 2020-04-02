import java.util.Stack;

public class Calculator {

    public static void main(String[] args) {
        String expression = "2+2*2";
        System.out.println(new Calculator().decide(expression));
    }

    public double decide(String expression) {
        String rpn = ExpressionToRPN(expression);
        return rpnToAnswer(rpn);
    }

    private String preparingExpression(String expression) {
        String preparedExpression = new String();

        for (int token = 0; token < expression.length(); token++) {
            char symbol = expression.charAt(token);
        }
        return preparedExpression;
    }

    //первый метод - выражение в обратную польскую нотацию
    private String ExpressionToRPN(String expression) {

        String current = "";
        Stack<Character> stack = new Stack<Character>();

        int priority;
        for (int i = 0; i < expression.length(); i++) {
            priority = getPriority(expression.charAt(i));

            if (priority == 0)
                current += expression.charAt(i);

            if (priority == 1)
                stack.push(expression.charAt(i));

            if (priority > 1) {
                current+=' ';
                while (!stack.empty()) {    //пока стек не пустой проверяем следущее:
                    if (getPriority(stack.peek()) >= priority) {  //если приоритет перхнего элемента нашего стека > or = приоритету текущего символа
                        current+=stack.pop();  //то мы достаем этот символ из стека и кидаем его в current
                    } else
                        break;
                }
                stack.push(expression.charAt(i));  //запихиваем в стек текущий символ
            }
            if (priority == -1) {
                current+=' ';
                while (getPriority(stack.peek()) != 1) {  //пока приоритет верхнего элемента стека не равен 1:
                    current+=stack.pop();
                }
                stack.pop(); //pop() - Возвращает элемент, находящийся в верхней части стэка, удаляя его в процессе.
            }
        }
        while (!stack.empty()) {  //пока стек не пустой мы делаем:
            current+=stack.pop();
        }
        return current;
    }

    //парсит польскую запись и переводит в ответ
    private double rpnToAnswer(String rpn) {
        String operand = new String();
        Stack<Double> stack = new Stack<Double>();

        for(int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') //если пробел, то запускаем заново
                continue;

            if (getPriority(rpn.charAt(i)) == 0) { //если попался символ(число), то засовываем всё число в операнд.
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0)
                    operand+=rpn.charAt(i++);
                if(i == rpn.length()) break;

                stack.push(Double.parseDouble(operand)); //преобразует String в Double
                operand = new String(); //снова инициализируем операнд, преобразуя в строку`
            }

//            Object pop()
//            Возвращает элемент, находящийся в верхней части стэка, удаляя его в процессе.

            if(getPriority(rpn.charAt(i)) > 1) { //если приоритет это мат символ, то:
                double a = stack.pop(), b = stack.pop(); //забрали два верхних числа

                if(rpn.charAt(i) == '+') stack.push(b + a);
                if(rpn.charAt(i) == '-') stack.push(b - a);
                if(rpn.charAt(i) == '*') stack.push(b * a);
                if(rpn.charAt(i) == '/') stack.push(b / a);
            }
        }
        return stack.pop();
    }

    //метод определяющий приоритет
    private int getPriority(char token) {
        if (token == '*' || token == '/') {
            return 3;
        } else if (token == '+' || token == '-') {
            return 2;
        } else if (token == '(') {
            return 1;
        } else if (token == ')') {
            return -1;
        } else
            return 0;
    }
}