import java.util.Stack;

public class Calculator {

    //первый метод - выражение в обратную польскую нотацию
    public static String ExpressionToRPN(String Expr) {
        String current = "";
        Stack<Character> stack = new Stack<Character>();

        int priority;
        for (int i = 0; i < Expr.length(); i++) {
            priority = getP(Expr.charAt(i));

            if (priority == 0) {
                current += Expr.charAt(i);
            }
            if (priority == 1) {
                stack.push(Expr.charAt(i));
            }
            if (priority > 1) {
                current+=' ';
                while (!stack.empty()) {    //пока стек не пустой проверяем следущее:
                    if (getP(stack.peek()) >= priority) {  //если приоритет перхнего элемента нашего стека > or = приоритету текущего символа
                        current+=stack.pop();  //то мы достаем этот символ из стека и кидаем его в current
                    } else
                        break; //иначе завершаем цикл
                }
                stack.push(Expr.charAt(i));  //запихиваем в стек текущий символ
            }
            if (priority == -1) {
                current+=' ';
                while (getP(stack.peek()) !=1) {  //пока приоритет верхнего элемента стека не равен 1:
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

    //обратную ПЗ переводим в ответ
    public static double RPNtoAnswer(String rpn) {
        String operand = new String();
        Stack<Double> stack = new Stack<Double>();

        for(int i = 0; i < rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {  //если пробел, то запускаем заново
                continue;
            }
            if (getP(rpn.charAt(i)) == 0) { //если попался символ(число), то засовываем всё число в операнд.
                while ( rpn.charAt(i) !=' ' && getP(rpn.charAt(i)) == 0) {
                    operand+=rpn.charAt(i++);
                }
                if(i == rpn.length()) {
                    break;
                }
                stack.push(Double.parseDouble(operand)); //преобразует String в Double
                operand = new String(); //снова инициализируем операнд, преобразуя в строку
            }
        }

        return 0;
    }

    //метод определяющий приоритет
    private static int getP(char token) {
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