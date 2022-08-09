//exception class to throw exception on invalid postfix expressions
class InvalidPostfixException extends Exception {
    public InvalidPostfixException(String mssg) {
        super(mssg);
    }
}
//exception class to throw exception on invalid infix expressions
class InvalidExprException extends Exception {
    public InvalidExprException(String mssg) {
        super(mssg);
    }
}

class Calculator {


    public int evaluatePostFix(String s) throws InvalidPostfixException {
        int k=0;
        //if first character is not a digit throw exception
        if ( s.charAt(0) == ' ') {
            while (s.charAt(k) == ' ') {
                ++k;
            }
            if(!Character.isDigit(s.charAt(k))){
            throw new InvalidPostfixException("E");}
        }
        if(!Character.isDigit(s.charAt(0))){
            throw new InvalidPostfixException("E");
        }

        //if there is no space in operator and digit throw exception
        for (int i = 1; i < s.length(); ++i) {
            while (s.charAt(i) == ' ') {
                ++i;
            }
            if ((!Character.isDigit(s.charAt(i - 1)) && s.charAt(i - 1) != ' ' && Character.isDigit(s.charAt(i))) ||
                    (Character.isDigit(s.charAt(i - 1)) && s.charAt(i - 1) != ' ' && !Character.isDigit(s.charAt(i)))) {
                throw new InvalidPostfixException("E");
            }

        }

        MyStack m = new MyStack();
        // while there are consecutive digits append them to a string to handle case of multi-digit numbers
        for (int i = 0; i < s.length(); ++i) {
            if (Character.isDigit(s.charAt(i)) && Character.isDigit(s.charAt(i + 1))) {
                String h = "";
                while (Character.isDigit(s.charAt(i))) {
                    h += s.charAt(i);
                    ++i;
                    if (i >= s.length()) {
                        break;
                    }
                }
                m.push(Integer.parseInt(h));
            } else if (s.charAt(i) != '+' && s.charAt(i) != '*' && s.charAt(i) != '-' && s.charAt(i) != ' ') {
                m.push(Character.getNumericValue(s.charAt(i)));
            } else {
                // if next character is an operator pop previous two elements and apply the operation in them
                //push the value vale in stack
                if (s.charAt(i) == '*') {
                    try {
                        int a = (Integer) m.pop();
                        int b = (Integer) m.pop();
                        m.push(a * b);
                    } catch (EmptyStackException e) {
                        throw new InvalidPostfixException("E");
                    }
                } else if (s.charAt(i) == '+') {
                    try {
                        int a = (Integer) m.pop();
                        int b = (Integer) m.pop();
                        m.push(a + b);
                    } catch (EmptyStackException e) {
                        throw new InvalidPostfixException("E");
                    }
                } else if (s.charAt(i) == '-') {
                    try {
                        int a = (Integer) m.pop();
                        int b = (Integer) m.pop();
                        m.push(b - a);
                    } catch (EmptyStackException e) {
                        throw new InvalidPostfixException("E");
                    }
                }
            }
        }
        try {
            //return the last left element of stack
            Object j = m.top();
            return (Integer) j;
        } catch (EmptyStackException e) {
            throw new InvalidPostfixException("E");
        }
    }


    public String convertExpression(String s) throws InvalidExprException {

        // writing to handle that no. of ( = no. of )
        int brkt = 0;
        boolean lastWasOp = true;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                continue;
            } else if (s.charAt(i) == '(') {
                brkt += 1;
            } else if (s.charAt(i) == ')') {
                brkt -= 1;
                if (brkt < 0) {
                    throw new InvalidExprException("E");
                }
                if (!Character.isDigit(s.charAt(i - 1)) && s.charAt(i) != ')') {
                    throw new InvalidExprException("E");
                }
            } else if (!Character.isDigit(s.charAt(i))) {
                if (lastWasOp) {
                    throw new InvalidExprException("E");
                } else {
                    lastWasOp = true;
                }
            } else {
                lastWasOp = false;
            }
        }
        if (brkt != 0) {
            throw new InvalidExprException("E");
        }


        // Now the expression is valid. We just need to convert from infix to postfix.


        try {
            String answer = "";
            MyStack stk = new MyStack();
            String curNumber = "";
            int i = 0;
            while (i < s.length()) {
                char curChar = s.charAt(i);
                if (curChar == ' ') {
                    i++;
                    continue;
                }
            //if the character is ( we push in stack
                if (curChar == '(') {
                    stk.push('(');
                } else if (Character.isDigit(curChar)) {
                    curNumber = "";
                    //to handle multi-digit inputs in string
                    while (i < s.length() && Character.isDigit(s.charAt(i))) {
                        curNumber += s.charAt(i);
                        i++;
                    }
                    answer += curNumber; //concatenating number to string
                    answer += ' ';
                    continue;
                }
                else if (curChar == '+' || curChar == '*' || curChar == '-') {

                    while (!stk.isEmpty() && "+-*".indexOf((char)stk.top()) >= 0 && (curChar == '-' || curChar == '+' ||
                            ((char)stk.top()) == '*' )) {
                        answer += stk.pop(); // concatenating operator
                        answer += ' ';
                    }
                    stk.push(curChar);
                }else if (curChar == ')') {
                    while (!stk.isEmpty() && ((char) stk.top() != '(')) {
                        answer += ((char) stk.pop());
                        answer += ' ';
                    }
                    stk.pop();
                } else{
                    throw new InvalidExprException("E");
                }
                i++;
            }
            while (!stk.isEmpty()) {
                answer += stk.pop();
                answer += ' ';
            }if (answer.charAt(answer.length() - 1) == ' ')
                return answer.substring(0, answer.length() - 1);
            else
                return answer;
        } catch (EmptyStackException e) {
            throw new InvalidExprException("E");
        }
    }
}

