import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.PrintWriter;

public class DataParser{
	
	public static void main(String[] args) throws IOException{
		URL url = new URL("https://www.quiverquant.com/sources/senatetrading");
		Scanner scanner = new Scanner(url.openStream());
		
		StringBuffer pageContent = new StringBuffer();
		boolean meaningfulData = false;
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			if(!meaningfulData) {
				if(line.contains("<table class=\"senate-trading-table\">")) {
					//14
					for(int skip = 0; skip<14;skip++) {
						line = scanner.nextLine();
					}
					meaningfulData = true;
				}
				else {
					continue;
				}
			}
			if(line.contains("<footer class=\"footer-outer\">")) {
				meaningfulData = false;
			}
			pageContent.append(line);
		}
		int lastDataMarker = pageContent.lastIndexOf("</tr>");
		pageContent = pageContent.delete(lastDataMarker+4, pageContent.length()-1);
		pageContent = removeBlankSpace(pageContent);
		
		
		
		PrintWriter out = new PrintWriter("FormattedSenateStockTrades.txt");
		
		
		out.println("");
		while(pageContent.length()>0) {
			StringBuffer buffer = new StringBuffer 
					(pageContent.substring(0, pageContent.indexOf("</tr>")+5));
			out.println(parseHtmlInputBoth(buffer));
			pageContent.delete(0, pageContent.indexOf("</tr>")+5);
		}
		
		out.close();
		
	} 
	
	public static List<String> parseHtmlInput(StringBuffer inputText) {
        List<String> formattedData = new ArrayList<>();
        

        String pattern = "<tr><td><ahref=\"../../stock/(.*?)\">(.*?)</a></td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>.*?</td></tr>";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(inputText);

        while (matcher.find()) {
            String stockCode = matcher.group(1);
            String date = matcher.group(3);
            String transactionType = matcher.group(4);
            String assetType = matcher.group(5);
            String amount = matcher.group(6);

            String formattedEntry = String.format("%s|%s|%s|%s|%s", stockCode, date, transactionType, assetType, amount);
            formattedData.add(formattedEntry);
        }

        return formattedData;
    }
	
	public static List<String> parseHtmlInputBoth(StringBuffer inputText) {
        List<String> formattedData = new ArrayList<>();

        String pattern = "<tr><td><ahref=\".*?\">(.*?)</a></td><td>(.*?)</td><td>(.*?)</td><td[^>]*>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td>.*?</tr>";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(inputText);

        while (matcher.find()) {
            String stockCode = matcher.group(1);
            String date = matcher.group(2);
            String user = matcher.group(3);
            String transactionType = matcher.group(4).replaceAll("<.*?>", "").trim();
            String assetType = matcher.group(5);
            String amount = matcher.group(6);

            String formattedEntry = String.format("%s|%s|%s|%s|%s|%s", stockCode, date, user, transactionType, assetType, amount);
            formattedData.add(formattedEntry);
        }

        return formattedData;
    }
	
	
	
	private static StringBuffer removeBlankSpace(StringBuffer sb) {
	    int currentEnd = -1;
	    for(int i = sb.length() - 1; i >= 0; i--) {
	        if (Character.isWhitespace(sb.charAt(i))) {
	            if (currentEnd == -1) {
	                currentEnd = i + 1;
	            }
	        } else {
	            // Moved from whitespace to non-whitespace
	            if (currentEnd != -1) {
	                sb.delete(i + 1, currentEnd);
	                currentEnd = -1;
	            }
	        }
	    }
	    // All leading whitespace
	    if (currentEnd != -1) {
	        sb.delete(0, currentEnd);
	    }
	    return sb;
	}
	
	public static StringBuffer parseHTMLLine(StringBuffer input) {
        // Define the regular expression patterns to match each field
        String symbolRegex = "<td><a[^>]*>(.*?)<\\/a><\\/td>";
        String dateRegex = "<td>(.*?)<\\/td>";
        String nameRegex = "<td>(.*?)<\\/td>";
        String typeRegex = "<td>(.*?)<\\/td>";
        String categoryRegex = "<td>(.*?)<\\/td>";
        String amountRegex = "<td>(.*?)<\\/td>";

        // Create a Pattern object for each field
        Pattern symbolPattern = Pattern.compile(symbolRegex);
        Pattern datePattern = Pattern.compile(dateRegex);
        Pattern namePattern = Pattern.compile(nameRegex);
        Pattern typePattern = Pattern.compile(typeRegex);
        Pattern categoryPattern = Pattern.compile(categoryRegex);
        Pattern amountPattern = Pattern.compile(amountRegex);

        // Create a Matcher object for the input StringBuffer
        Matcher symbolMatcher = symbolPattern.matcher(input);
        Matcher dateMatcher = datePattern.matcher(input);
        Matcher nameMatcher = namePattern.matcher(input);
        Matcher typeMatcher = typePattern.matcher(input);
        Matcher categoryMatcher = categoryPattern.matcher(input);
        Matcher amountMatcher = amountPattern.matcher(input);

        // Initialize a StringBuffer to store the extracted data
        StringBuffer result = new StringBuffer();

        // Find and append the extracted data for each field
        if (symbolMatcher.find()) {
            String symbol = symbolMatcher.group(1).trim();
            result.append(symbol).append("|");
        }
        if (dateMatcher.find()) {
            String date = dateMatcher.group(1).trim();
            result.append(date).append("|");
        }
        if (nameMatcher.find()) {
            String name = nameMatcher.group(1).trim();
            result.append(name).append("|");
        }
        if (typeMatcher.find()) {
            String type = typeMatcher.group(1).trim();
            result.append(type).append("|");
        }
        if (categoryMatcher.find()) {
            String category = categoryMatcher.group(1).trim();
            result.append(category).append("|");
        }
        if (amountMatcher.find()) {
            String amount = amountMatcher.group(1).trim();
            result.append(amount);
        }

        return result;
    }
	

}
