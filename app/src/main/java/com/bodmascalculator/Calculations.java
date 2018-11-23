package com.bodmascalculator;

import android.content.ComponentName;
import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by mihir on 18-08-2018.
 */

public class Calculations {

    final private ArrayList<String> unaryOperators = new ArrayList<>();
    final private ArrayList<String> binaryOperators = new ArrayList<>();
    final private ArrayList<String> constants = new ArrayList<>();

    private Context ctx;

    public ArrayList<String> numbers = new ArrayList<>();
    //private ArrayList<String> operators = new ArrayList<>();
    //log base -> 10, ln base -> Euler's number = e ~ 2.718
    public String answer;

    public boolean isExp = false;

    /**
     * currentStatus = {const, unary, binary, num, po, pc};
     */
    public String currentStatus = "null";


    public Calculations(Context ct){
        ctx = ct;
        unaryOperators.add("!");
        unaryOperators.add("%");
        unaryOperators.add("ln");
        unaryOperators.add("log");
        unaryOperators.add("dms");
        unaryOperators.add("deg");
        unaryOperators.add("exp");
        unaryOperators.add("sin");
        unaryOperators.add("cos");
        unaryOperators.add("tan");
        unaryOperators.add("sin-1");
        unaryOperators.add("tan-1");
        unaryOperators.add("cos-1");
        unaryOperators.add("sinh");
        unaryOperators.add("tanh");
        unaryOperators.add("cosh");
        unaryOperators.add("sinh-1");
        unaryOperators.add("tan-1");
        unaryOperators.add("cos-1");
        unaryOperators.add("1/x");
        unaryOperators.add("sq");
        unaryOperators.add("cb");
        unaryOperators.add("sqrt");
        unaryOperators.add("cbrt");
        unaryOperators.add("10^x");
        unaryOperators.add("e^x");

        /*
         * binaryOperators in order...
         */
        binaryOperators.add("rt");
        binaryOperators.add("^");
        binaryOperators.add("mod");
        binaryOperators.add("/");
        binaryOperators.add("*");
        binaryOperators.add("+");
        binaryOperators.add("-");

        constants.add("pi");
        constants.add("e");
    }

    public boolean clear(){
        try {
            numbers.clear();
            answer = "0";
            calc(numbers);
        } catch (Exception e){
            calc(numbers);
            return false;
        }
        currentStatus = "null";
        return true;
    }

    public boolean ce(){
        try {
            switch (currentStatus){
                case "num":
                    numbers.remove(numbers.size()-1);
                    break;
                case "const":
                    numbers.remove(numbers.size()-1);
                    break;
                case "binary":
                    numbers.remove(numbers.size()-1);
                    break;
                case "unary":
                    if (unaryOperators.contains(numbers.get(numbers.size()-1))){
                        numbers.remove(numbers.size()-1);
                        numbers.remove(numbers.size()-1);
                        int bracketOffset = -1;
                        for (int i = numbers.size()-1 ; i == 0 ; i--){
                            if (numbers.get(i).equals("]")){
                                bracketOffset--;
                            }
                            if (numbers.get(i).equals("[")){
                                bracketOffset++;
                                if (bracketOffset == 0){
                                    numbers.remove(i);
                                }
                            }
                        }
                    } else {
                        numbers.remove(numbers.size()-1);
                        int bracketOffset = -1;
                        for (int i = numbers.size()-1 ; i == 0 ; i--){
                            if (numbers.get(i).equals("}")){
                                bracketOffset--;
                            }
                            if (numbers.get(i).equals("{")){
                                bracketOffset++;
                                if (bracketOffset == 0){
                                    numbers.remove(i-1);
                                    numbers.remove(i-1);
                                }
                            }
                        }
                    }
                    break;
                case "po":
                    numbers.remove(numbers.size()-1);
                    break;
                case "pc":
                    numbers.remove(numbers.size()-1);
                    break;
            }
            if (numbers.size() == 0){
                currentStatus = "null";
                return true;
            }
            currentStatus = getStatus(numbers.size()-1);
            calc(numbers);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean bs(){
        if (currentStatus.equals("num")){
            try {
                numbers.set(numbers.size()-1, formatNumber(numbers.get(numbers.size()-1).substring(0, numbers.get(numbers.size()-1).length()-1)));
            } catch (Exception e){
                numbers.set(numbers.size()-1, "0");
                return false;
            }
        }
        calc(numbers);
        return true;
    }

    public boolean numberClicked(String number){
        switch (currentStatus) {
            case "null":
                numbers.add(number);
                break;
            case "num":
                numbers.set(numbers.size() - 1, formatNumber(numbers.get(numbers.size() - 1) + number));
                break;
            case "const":
                numbers.add("*");
                numbers.add(number);
                break;
            case "unary":
                numbers.add("*");
                numbers.add(number);
                break;
            case "binary":
                numbers.add(number);
                break;
            case "po":
                numbers.add(number);
                break;
            case "pc":
                numbers.add("*");
                numbers.add(number);
                break;
            default :
                Toast.makeText(ctx, "Error while numberClicked(), currentStatus = " + currentStatus , Toast.LENGTH_SHORT).show();
                break;
        }
        currentStatus = "num";
        calc(numbers);
        return true;
    }

    public boolean decimalClicked(){
        if (currentStatus.equals("num")){
            if (numbers.get(numbers.size()-1).contains(".")){
                return false;
            } else {
                numbers.set(numbers.size()-1, numbers.get(numbers.size()-1) + ".");
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean operatorClicked(String operator) {
        Log.d("calc", "Operator : " + operator);
        try {
            if (operator.equals("±")){
                numbers.set(numbers.size()-1, formatNumber(String.valueOf((Double.parseDouble(numbers.get(numbers.size()-1)) * -1))));
                calc(numbers);
                return true;
            }
        } catch (Exception e){
            calc(numbers);
            return false;
        }

        if (constants.contains(operator)){
            switch (currentStatus){
                case "null":
                    numbers.add(operator);
                    break;
                case "num":
                    numbers.add("*");
                    numbers.add(operator);
                    break;
                case "const":
                    numbers.add("*");
                    numbers.add(operator);
                    break;
                case "unary":
                    numbers.add("*");
                    numbers.add(operator);
                    break;
                case "binary":
                    numbers.add(operator);
                    break;
                case "po":
                    numbers.add(operator);
                    break;
                case "pc":
                    numbers.add("*");
                    numbers.add(operator);
                    break;
                default :
                    Toast.makeText(ctx, "Error while constants, currentStatus = " + currentStatus , Toast.LENGTH_SHORT).show();
                    break;
            }
            currentStatus = "const";
        } else if (unaryOperators.contains(operator)){
            switch (currentStatus){
                case "null":
                    numbers.add("0");
                    if (operator.equals("!") || operator.equals("%") || operator.equals("sq") || operator.equals("cb")){
                        numbers.add(numbers.size()-1, "[");
                        numbers.add("]");
                        numbers.add(operator);
                    } else {
                        numbers.add(numbers.size()-1, operator);
                        numbers.add(numbers.size()-1, "{");
                        numbers.add("}");
                    }
                    currentStatus = "unary";
                    break;
                case "num":
                    if (operator.equals("!") || operator.equals("%") || operator.equals("sq") || operator.equals("cb")){
                        numbers.add(numbers.size()-1, "[");
                        numbers.add("]");
                        numbers.add(operator);
                    } else {
                        numbers.add(numbers.size()-1, operator);
                        numbers.add(numbers.size()-1, "{");
                        numbers.add("}");
                    }
                    currentStatus = "unary";
                    break;
                case "const":
                    if (operator.equals("!") || operator.equals("%") || operator.equals("sq") || operator.equals("cb")){
                        numbers.add(numbers.size()-1, "[");
                        numbers.add("]");
                        numbers.add(operator);
                    } else {
                        numbers.add(numbers.size()-1, operator);
                        numbers.add(numbers.size()-1, "{");
                        numbers.add("}");
                    }
                    currentStatus = "unary";
                    break;
                case "unary":
                    if (numbers.get(numbers.size()-1).equals("!") || numbers.get(numbers.size()-1).equals("%") || numbers.get(numbers.size()-1).equals("sq") || numbers.get(numbers.size()-1).equals("cb")){
                        if (operator.equals("!") || operator.equals("%") || operator.equals("sq") || operator.equals("cb")){
                            int bracketOffset = 0;
                            for (int i = numbers.size()-1 ; i == 0 ; i--){
                                if (numbers.get(i).equals("]")){
                                    bracketOffset--;
                                }
                                if (numbers.get(i).equals("[")){
                                    bracketOffset++;
                                    if (bracketOffset == 0){
                                        numbers.add(i, "[");
                                        numbers.add("]");
                                        numbers.add(operator);
                                        break;
                                    }
                                }
                            }
                        } else {
                            int bracketOffset = 0;
                            for (int i = numbers.size()-1 ; i == 0 ; i--){
                                if (numbers.get(i).equals("]")){
                                    bracketOffset--;
                                }
                                if (numbers.get(i).equals("[")){
                                    bracketOffset++;
                                    if (bracketOffset == 0){
                                        numbers.add(i, operator);
                                        numbers.add(i, "{");
                                        numbers.add("}");
                                        break;
                                    }
                                }
                            }
                        }
                        currentStatus = "unary";
                    } else {
                        if (operator.equals("!") || operator.equals("%") || operator.equals("sq") || operator.equals("cb")){
                            int bracketOffset = 0;
                            for (int i = numbers.size()-1 ; i == 0 ; i--){
                                if (numbers.get(i).equals("}")){
                                    bracketOffset--;
                                }
                                if (numbers.get(i).equals("{")){
                                    bracketOffset++;
                                    if (bracketOffset == 0){
                                        numbers.add(i-1, "[");
                                        numbers.add("]");
                                        numbers.add(operator);
                                        break;
                                    }
                                }
                            }
                        } else {
                            int bracketOffset = 0;
                            for (int i = numbers.size()-1 ; i == 0 ; i--){
                                if (numbers.get(i).equals("}")){
                                    bracketOffset--;
                                }
                                if (numbers.get(i).equals("{")){
                                    bracketOffset++;
                                    if (bracketOffset == 0){
                                        numbers.add(i, operator);
                                        numbers.add(i, "{");
                                        numbers.add("}");
                                        break;
                                    }
                                }
                            }
                        }
                        currentStatus = "unary";
                    }
                    break;
                case "binary":
                    break;
                case "po":
                    break;
                case "pc":
                    break;
                default :
                    Toast.makeText(ctx, "Error while unary, currentStatus = " + currentStatus , Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (binaryOperators.contains(operator)){
            switch (currentStatus){
                case "null":
                    numbers.add("0");
                    numbers.add(operator);
                    currentStatus = "binary";
                    break;
                case "num":
                    numbers.add(operator);
                    currentStatus = "binary";
                    break;
                case "const":
                    numbers.add(operator);
                    currentStatus = "binary";
                    break;
                case "unary":
                    numbers.add(operator);
                    currentStatus = "binary";
                    break;
                case "binary":
                    numbers.set(numbers.size()-1, operator);
                    currentStatus = "binary";
                    break;
                case "po":
                    break;
                case "pc":
                    numbers.add(operator);
                    currentStatus = "binary";
                    break;
                default :
                    Toast.makeText(ctx, "Error while binary, currentStatus = " + currentStatus , Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        return false;
    }

    public boolean parent_openClicked() {
        switch (currentStatus) {
            case "null":
                numbers.add("(");
                break;
            case "num":
                numbers.add("*");
                numbers.add("(");
                break;
            case "const":
                numbers.add("*");
                numbers.add("(");
                break;
            case "unary":
                numbers.add("*");
                numbers.add("(");
                break;
            case "binary":
                numbers.add("(");
                break;
            case "po":
                numbers.add("(");
                break;
            case "pc":
                numbers.add("*");
                numbers.add("(");
                break;
            default :
                Toast.makeText(ctx, "Error while numberClicked(), currentStatus = " + currentStatus , Toast.LENGTH_SHORT).show();
                break;
        }
        currentStatus = "po";
        calc(numbers);
        return true;
    }

    public boolean parent_closeClicked() {
        if (!canCloseParent()){
            Toast.makeText(ctx, "No open parenthesis to compensate", Toast.LENGTH_SHORT).show();
            return false;
        }
        switch (currentStatus) {
            case "num":
                numbers.add(")");
                currentStatus = "pc";
                break;
            case "const":
                numbers.add(")");
                currentStatus = "pc";
                break;
            case "unary":
                numbers.add(")");
                currentStatus = "pc";
                break;
            case "binary":
                break;
            case "po":
                break;
            case "pc":
                numbers.add(")");
                break;
            default :
                Toast.makeText(ctx, "Error while numberClicked(), currentStatus = " + currentStatus , Toast.LENGTH_SHORT).show();
                break;
        }
        calc(numbers);
        return true;
    }

    private boolean canCloseParent(){
        int bracketOffset = 0;
        for (String number : numbers){
            if (number.equals("(")){
                bracketOffset++;
            }
            if (number.equals(")")){
                bracketOffset--;
            }
        }
        return (bracketOffset > 0);
    }


    public String getCurrentNumber(){
        if (!(currentStatus.equals("num"))){
            Log.d("calc", "current Number returned : 0");
            return "0";
        } else {
            Log.d("calc", "current Number returned : " + numbers.get(numbers.size()-1));
            return numbers.get(numbers.size()-1);
        }
    }

    public boolean evaluateAnswer(){
        if (numbers.contains("(")){
            if (canCloseParent()){
                int bracketOffset = 0;
                for (String number : numbers){
                    if (number.equals("(")){
                        bracketOffset++;
                    }
                    if (number.equals(")")){
                        bracketOffset--;
                    }
                }
                for (int i = 0 ; i < bracketOffset ; i++){
                    numbers.add(")");
                }
            }
        }
        answer = doBODMAS(numbers);
        calc(numbers);
        return true;
    }

    public String calc(ArrayList<String> numbers){
        String num = "";
        for (String number : numbers){
            num += number + " ";
        }
        Log.d("calc", num);
        return num;
    }

    @NonNull
    private String doBODMAS(ArrayList<String> numbers) {
        if (contains(constants, numbers)){
            for (int i = 0 ; i < numbers.size() ; i++){
                if (constants.contains(numbers.get(i))){
                    numbers.set(i, evaluateConstant(numbers.get(i)));
                }
            }
        }
        while (numbers.size() != 1){
            Log.d("calc", "doBodmas:");
            calc(numbers);
            Log.d("calc", "contains ( = " + numbers.contains("("));
            if (numbers.contains("(")){
                int bracketOffset = 0;
                for (int i = numbers.size()-1 ; i >= 0 ; i--){
                    Log.d("calc", "at i = " + i);
                    if (numbers.get(i).equals(")")){
                        bracketOffset--;
                        for (int j = i-1 ; j >= 0 ; j--){
                            Log.d("calc", "at i = " + j);
                            if (numbers.get(j).equals(")")){
                                bracketOffset--;
                            }
                            if (numbers.get(j).equals("(")){
                                bracketOffset++;
                                if (bracketOffset == 0){
                                    Log.d("calc", "satisfied at j = " + j);
                                    ArrayList<String> nums = new ArrayList<>();
                                    for (int k = j+1 ; k < i ; k++){
                                        nums.add(numbers.get(k));
                                    }
                                    calc(nums);
                                    for (int k = j ; k < i ; k++){
                                        numbers.remove(j);
                                    }
                                    numbers.set(j, doBODMAS(nums));
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            } else {
                if (contains(unaryOperators, numbers)){
                    for (int i = 0 ; i < numbers.size() ; i++){
                        if (unaryOperators.contains(numbers.get(i))){
                            if (numbers.get(i).equals("!") || numbers.get(i).equals("%") || numbers.get(i).equals("sq") || numbers.get(i).equals("cb")){
                                int bracketOffset = 0;
                                for (int j = i-1 ; j >= 0 ; j--){
                                    if (numbers.get(j).equals("]")){
                                        bracketOffset--;
                                    }
                                    if (numbers.get(j).equals("[")){
                                        bracketOffset++;
                                        if (bracketOffset == 0){
                                            ArrayList<String> nums = new ArrayList<>();
                                            Log.d("calc", "i:"+i);
                                            Log.d("calc", "j:"+j);
                                            for (int k = j+1 ; k == i-2 ; k++){
                                                nums.add(numbers.get(k));
                                            }
                                            if (nums.size() == 1){
                                                String ans = evaluateUnary(numbers.get(i), nums.get(0));
                                                for (int k = j ; k < i ; k++){
                                                    numbers.remove(j);
                                                }
                                                numbers.set(j, ans);
                                            } else {
                                                for (int k = j ; k < i ; k++){
                                                    numbers.remove(j);
                                                }
                                                numbers.set(j, doBODMAS(nums));
                                            }
                                            i = 0;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                int bracketOffset = 0;
                                for (int j = i+1 ; j < numbers.size() ; j++){
                                    if (numbers.get(j).equals("{")){
                                        bracketOffset++;
                                    }
                                    if (numbers.get(j).equals("}")){
                                        bracketOffset--;
                                        if (bracketOffset == 0){
                                            ArrayList<String> nums = new ArrayList<>();
                                            Log.d("calc", "in Binary eval:");
                                            for (int k = i+2 ; k < j ; k++){
                                                nums.add(numbers.get(k));
                                            }
                                            Log.d("calc", "i:"+i);
                                            Log.d("calc", "j:"+j);
                                            calc(nums);
                                            if (nums.size() == 1){
                                                String ans = evaluateUnary(numbers.get(i), nums.get(0));
                                                for (int k = i ; k < j ; k++){
                                                    numbers.remove(i);
                                                }
                                                numbers.set(i, ans);
                                            } else {
                                                for (int k = i ; k < j ; k++){
                                                    numbers.remove(i);
                                                }
                                                numbers.set(i, doBODMAS(nums));
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (contains(binaryOperators, numbers)){
                    for (int i = 0 ; i < binaryOperators.size() ; i++){
                        if (numbers.contains(binaryOperators.get(i))){
                            for (int j = 0 ; j < numbers.size() ; j++){
                                if (numbers.get(j).equals(binaryOperators.get(i))){
                                    String ans = (evaluateBinary(numbers.get(j-1), numbers.get(j), numbers.get(j+1)));
                                    numbers.remove(j-1);
                                    numbers.remove(j-1);
                                    numbers.set(j-1, ans);
                                    i = 0;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        currentStatus = "num";
        return formatNumber(numbers.get(0));
    }

    private boolean contains(ArrayList<String> source, ArrayList<String> target){
        for (String val : target){
            if (source.contains(val)){
                return true;
            }
        }
        return false;
    }

    private String getStatus(int index){
        if (constants.contains(numbers.get(index))){
            return "const";
        } else if (unaryOperators.contains(numbers.get(index)) || numbers.get(index).contains("{") || numbers.get(index).contains("}") ||
                numbers.get(index).contains("[") || numbers.get(index).contains("]")){
            return "unary";
        } else if (binaryOperators.contains(numbers.get(index))){
            return "binary";
        } else if (numbers.get(index).equals("(")){
            return "po";
        } else if (numbers.get(index).equals(")")){
            return "pc";
        } else if (numbers.get(index).equals("0")){
            if (numbers.size()-1 == index){
                numbers.remove(index);
            }
            return "null";
        } else {
            return "num";
        }
    }


    private String formatNumber(String number){
        Log.d("calc", "Format Number : " + number);
        if (number == null || number.equals("") || number.isEmpty()){
            return "0";
        }
        try {
            if (Double.parseDouble(number) == Long.parseLong(number.split("\\.")[0])){
                return String.valueOf(Long.parseLong(number.split("\\.")[0]));
            } else {
                return String.valueOf(Double.parseDouble(number));
            }
        } catch (NumberFormatException nfe){
            try {
                return String.valueOf(Double.parseDouble(number));
            } catch (NumberFormatException nfe2){
                try {
                    number = number.substring(0, number.length()-1);
                    return String.valueOf(Double.parseDouble(number));
                } catch (Exception e){
                    return number;
                }
            }
        }
    }


    private String evaluateConstant(String notation){
        Log.d("calc", "constant : " + notation);
        String value = "error";
        switch (notation) {
            case "pi":
                value = pi();
                break;
            case "e":
                value = e();
                break;
            default:
                Toast.makeText(ctx, "Error while evaluating constants : " + notation, Toast.LENGTH_SHORT).show();
                break;
        }
        return value;
    }

    private String evaluateUnary(String operation, String value){
        Log.d("calc", "Unary : " + operation + ", " + value);
        String answer = "error";
        switch (operation){
            case "!":
                answer = factorial(value);
                break;
            case "%":
                answer = percent(value);
                break;
            case "ln":
                answer = ln(value);
                break;
            case "log":
                answer = log(value);
                break;
            case "dms":
                answer = dms(value);
                break;
            case "deg":
                answer = deg(value);
                break;
            case "exp":
                answer = exp(value);
                break;
            case "sin":
                answer = sin(value);
                break;
            case "cos":
                answer = cos(value);
                break;
            case "tan":
                answer = tan(value);
                break;
            case "sin-1":
                answer = asin(value);
                break;
            case "cos-1":
                answer = acos(value);
                break;
            case "tan-1":
                answer = atan(value);
                break;
            case "sinh":
                answer = sinh(value);
                break;
            case "cosh":
                answer = cosh(value);
                break;
            case "tanh":
                answer = tanh(value);
                break;
            case "sinh-1":
                answer = asinh(value);
                break;
            case "cosh-1":
                answer = acosh(value);
                break;
            case "tanh-1":
                answer = atanh(value);
                break;
            case "sq":
                answer = indices(value, "2");
                break;
            case "cb":
                answer = indices(value, "3");
                break;
            case "1/x":
                answer = division("1", value);
                break;
            case "sqrt":
                answer = root(value, "2");
                break;
            case "cbrt":
                answer = root(value, "3");
                break;
            case "10^x":
                answer = indices("10", value);
                break;
            case "e^x":
                answer = indices(e(), value);
                break;
            default:
                Toast.makeText(ctx, "Error in evaluating Unary operation : " + operation, Toast.LENGTH_SHORT).show();
                break;
        }
        Log.d("calc", "returned : " + answer);
        try {
            return formatNumber(answer);
        } catch (Exception e){
            return answer;
        }
    }

    private String evaluateBinary(String number1, String operation, String number2){
        Log.d("calc", "binary : " + number1 + ", " + operation + ", " + number2);
        String answer = "error";
        switch (operation){
            case "rt":
                answer = root(number1, number2);
                break;
            case "^":
                answer = indices(number1, number2);
                break;
            case "mod":
                answer = mod(number1, number2);
                break;
            case "/":
                answer = division(number1, number2);
                break;
            case "*":
                answer = multiplication(number1, number2);
                break;
            case "+":
                answer = addition(number1, number2);
                break;
            case "-":
                answer = subtraction(number1, number2);
                break;
            default:
                Toast.makeText(ctx, "error in evaluating Binary operation : " + operation, Toast.LENGTH_SHORT).show();
                break;
        }
        try {
            return formatNumber(answer);
        } catch (Exception e){
            return answer;
        }
    }

    @NonNull
    private String pi(){
        return String.valueOf(Math.PI);
    }

    @NonNull
    private String e(){
        return String.valueOf(Math.E);
    }


    @NonNull
    private String factorial(String num){
        try {
            int intNum = Integer.valueOf(num);
            double doubleNum = Double.valueOf(num);
            if (intNum == doubleNum){
                return String.valueOf(fact(intNum));
            } else {
                return String.valueOf(gammaFunction(doubleNum + 1));
            }
        } catch (NumberFormatException nfe){
            return String.valueOf(gammaFunction(Double.valueOf(num)+1));
        }
    }
    private int fact(int num){
        if (num == 0) {
            return 1;
        } else {
            return (num * fact(num - 1));
        }
    }
    private double gammaFunction(double x) {
        double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
        double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
                + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
                +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
        return Math.exp(tmp + Math.log(ser * Math.sqrt(2 * Math.PI)));
    }

    @NonNull
    private String percent(String num){
        return String.valueOf((Double.parseDouble(num) / 100));
    }

    @NonNull
    private String ln(String num){
        return String.valueOf(Math.log(Double.parseDouble(num)));
    }

    @NonNull
    private String log(String num){
        return String.valueOf(Math.log10(Double.parseDouble(num)));
    }

    @NonNull
    private String dms(String num){
        // 23.34 -> 23.2024
        double dd = Double.parseDouble(num);
        int d = (int) dd;
        int m = (int) ((dd - d) * 60);
        int s = (int) (Math.round(((dd - ((double) d)) - (((double) m) / 60)) * 3600));
        String y = String.valueOf(m) + String.valueOf(s);
        return d + "." + y;
    }

    @NonNull
    private String deg(String num){
        //23.34 -> 23.566666666667
        String[] dms = num.split("\\.");
        int d = Integer.valueOf(dms[0]);
        String m = "0";
        String s = "0";
        int i = 0;
        while (!(i == dms[1].length())){
            if (i < 2){
                m += dms[1].charAt(i);
            } else if (i < 4){
                s += dms[1].charAt(i);
            } else {
                if (!(s.contains("."))){
                    s += '.';
                }
                s += dms[1].charAt(i);
            }
            i++;
        }
        double ms = ((Double.parseDouble(m)) / 60) + ((Double.parseDouble(s)) /3600);
        return String.valueOf(d + ms);
    }

    @NonNull
    private String exp(String value){
        isExp = true;
        return value + "E0";
    }

    @NonNull
    private String sin(String value){
        return String.valueOf(Math.sin(Double.parseDouble(value)));
    }

    @NonNull
    private String cos(String value){
        return String.valueOf(Math.cos(Double.parseDouble(value)));
    }

    @NonNull
    private String tan(String value){
        return String.valueOf(Math.tan(Double.parseDouble(value)));
    }

    @NonNull
    private String asin(String value){
        return String.valueOf(Math.asin(Double.parseDouble(value)));
    }

    @NonNull
    private String acos(String value){
        return String.valueOf(Math.acos(Double.parseDouble(value)));
    }

    @NonNull
    private String atan(String value){
        return String.valueOf(Math.atan(Double.parseDouble(value)));
    }

    @NonNull
    private String sinh(String value){
        return String.valueOf(Math.sinh(Double.parseDouble(value)));
    }

    @NonNull
    private String cosh(String value){
        return String.valueOf(Math.cosh(Double.parseDouble(value)));
    }

    @NonNull
    private String tanh(String value){
        return String.valueOf(Math.tanh(Double.parseDouble(value)));
    }

    @NonNull
    private String asinh(String value){
        double x = Double.parseDouble(value);
        if (x == Double.NEGATIVE_INFINITY){
            return value;
        } else {
            return String.valueOf(Math.log(x + Math.sqrt(x * x + 1)));
        }
    }

    @NonNull
    private String acosh(String value){
        double x = Double.parseDouble(value);
        return String.valueOf(Math.log(x + Math.sqrt(x * x - 1)));
    }

    @NonNull
    private String atanh(String value){
        double x = Double.parseDouble(value);
        return String.valueOf(Math.log((1+x)/(1-x)) / 2);
    }


    @NonNull
    private String root(String num1, String num2){
        double num = Double.parseDouble(num1);
        double root = Double.parseDouble(num2);
        return String.valueOf(Math.pow(Math.exp (1/root), Math.log(num)));
    }

    @NonNull
    private String indices(String num1, String num2){
        return String.valueOf(Math.pow(Double.parseDouble(num1), Double.parseDouble(num2)));
    }

    @NonNull
    private String mod(String num1, String num2){
        return String.valueOf((Double.parseDouble(num1) % Double.parseDouble(num2)));
    }

    @NonNull
    private String division(String num1, String num2){
        return String.valueOf((Double.parseDouble(num1) / Double.parseDouble(num2)));
    }

    @NonNull
    private String multiplication(String num1, String num2){
        return String.valueOf((Double.parseDouble(num1) * Double.parseDouble(num2)));
    }

    @NonNull
    private String addition(String num1, String num2){
        return String.valueOf((Double.parseDouble(num1) + Double.parseDouble(num2)));
    }

    @NonNull
    private String subtraction(String num1, String num2){
        return String.valueOf((Double.parseDouble(num1) - Double.parseDouble(num2)));
    }
}