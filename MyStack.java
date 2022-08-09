// make emptyexceptionclass
class EmptyStackException extends Exception {
    public EmptyStackException(String mssg) {
        super(mssg);
    }
}

class MyStack implements StackInterface {

    public int n = 1; // n represents the stack size allotted
    Object[] arr = new Object[n]; // arr is the array of size n which implements the stack
    public int tp = -1;

    public void push(Object o) {
        if (tp + 1 == n) {
            n *= 2;
            Object[] brr = new Object[n];  // defining array brr with double size of arr and copying elemets in brr
            for (int i = 0; i <= tp; ++i) {
                brr[i] = arr[i];
            }

            if(o!=null){
                tp += 1; // pushing element in stack and updating tp count
            brr[tp] = o;}
            arr = brr;
        } else {

            if(o!=null){
                tp += 1;
            arr[tp] = o;}
        }
    }
 // if tp=-1 i.e stack is empty throws exception otherwise removes top element
    public Object pop() throws EmptyStackException {
        if (tp == -1) {
            throw new EmptyStackException("Empty Stack Exception");
        } else {
            tp -= 1;
            return arr[tp + 1];
        }
    }
    // if tp=-1 i.e stack is empty throws exception otherwise returns top element
    public Object top() throws EmptyStackException {
        if (tp == -1) {
            throw new EmptyStackException("Empty Stack Exception");
        } else {
            return arr[tp];
        }
    }

    // returns false if stack is empty true otherwise
    public boolean isEmpty() {
        if (tp == -1) {
            return true;
        }
        return false;
    }

    public String s = "[";
// coverts input to string
    public String toString() {
        if (tp == -1) {
            return "[]";
        } else {
            for (int i = tp; i >= 0; --i) {
                s = s + String.valueOf(arr[i]);
                if (i != 0) {
                    s += ", ";
                } else {
                    s += ']';
                }
            }
            return s;
        }
    }

}




