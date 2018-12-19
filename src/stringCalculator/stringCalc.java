package stringCalculator;

public class stringCalc{
	//generates sum from string and delimiters provided
    public static int Add(String numbers){
        
        int sum = 0;
        String negString = "";
        boolean negFlag = false;
        boolean commaFlag = false;
        
        String numberComponent[];
        String delimiterString;
        String delimiters[];
        String delimiterRegEx = "";
        
        System.out.println("Working string:\n"+numbers);
        //separate delimiter and number strings
        String newlineSplit[] = numbers.split("\n");
        
        if(numbers == null || numbers.isEmpty()){ //check for empty or null input
            System.out.println("String is empty or null\n");
            return 0;
        }
        else if(newlineSplit[0].charAt(0) != '/' || newlineSplit[0].charAt(1) != '/') { //check if string begins with '//'
        	System.out.println("String is invalid\n");
        	return 0;
        }
        else { //get here if string has content and begins with '//'
        	//take delimiter string of first split and ignore '//'
        	delimiterString = newlineSplit[0].substring(2);
        	
        	//check if ',' is one of the delimiters (do this since comma is also delimiter in delimiter string
        	if(delimiterString.contains(",,") || delimiterString.equals(",")) {
        		commaFlag = true;
        	}
        	
        	delimiters = delimiterString.split(",");
        	
        	//add comma as delimiter if it existed (split erases comma if used as delimiter previously)
        	if(commaFlag == true) {
        		delimiterRegEx += "\\,";
    			commaFlag = false;
    		}
        	
        	//for each delimiter found
        	for(String temp:delimiters) {
        		//escape all characters
        		temp = temp.replace(""+temp.charAt(0), "\\"+temp.charAt(0));
        		//continuously add to new regex (separate with OR for multiple delimiters)
        		if(delimiterRegEx.length() == 0) {
        			delimiterRegEx += temp;
        		}
        		else {
        			delimiterRegEx += "|"+temp;
        		}
        	}
        }
        
        System.out.println("Start of calculation...");
        //split using regex generated, get every number as string
        numberComponent = newlineSplit[1].split(delimiterRegEx);
        
        for (String tempVal: numberComponent){
        	if(Integer.valueOf(tempVal) < 0) { //raise error and record number if negative
        		if(negString.length() == 0) {
        			System.out.println("Negatives not allowed");
        			negFlag = true;
        			negString += tempVal;
        		}
        		else {
        			negString += ","+tempVal;
        		}
        		continue;
        	}
        	else if(Integer.valueOf(tempVal) > 1000) { //raise message and ignore if number is >1000
        		System.out.println("Large number found and ignored");
        		continue;
        	}
        	else { //if passes all checks, add to sum
        		sum += Integer.valueOf(tempVal);
        	}
            
        }
        
        //display end message, depending if negatives were found or not
        if(negFlag == true) {
        	System.out.println("Negative number(s) found: "+negString+"\n");
        }
        else {
        	System.out.println("Sum is "+sum);
        	System.out.println("End of calculation!\n");
        }
        return 0;
    }

    //main acts as driver
    public static void main(String []args){
        
        int test;
        
        //standard test cases
        test = Add("//;\n1;3;4"); //regular
        test = Add("//$\n1$2$3"); //regular
        test = Add("//@\n2@3@8"); //regular
        test = Add("//$\n1$-2$-3"); //negative
        test = Add("/mklk"); //invalid
        test = Add(""); //empty
        //bonus test cases
        test = Add("//@,*\n2@3*8"); //multiple delimiter test case
        test = Add("//,\n2,1001"); //over 1000 test case
        test = Add("//???\n3???8"); //delimiter variable length test case
        test = Add("//???,***\n3???8***1"); //multiple delimiter with variable length
        test = Add("//???,***\n3???-8***1");
        test = Add("//???,***\n3???8***1001");
        test = Add("//?????????,}}}\n3?????????8}}}1001");
        
        return;
    }
}
