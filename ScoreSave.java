package Tetris;

import java.io.*;
import java.util.*;
import java.awt.Component;


public class ScoreSave {
	public static String enter = System.getProperty("line.separator");
	
	// 파일에서 데이터 읽기
	public static String read(String file) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = null;
			String lineData = "";
			
			while((line = in.readLine()) != null) {
				lineData += line + "&&"; // 문자열로 만듦, 정보간 '&&' 키로 나눔
			}
			in.close();
			return lineData;
		} catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static void sorting(String data) {
        String[] lines = data.split("&&");
        clearFile("scores.txt"); // 파일 초기화
        // 배열을 정렬하기 위해 Comparator를 사용하여 비교 규칙을 정의
        Comparator<String> comparator = new Comparator<String>() {
            public int compare(String o1, String o2) {
                // 문자열을 공백(" ")을 기준으로 분리하여 숫자 부분을 비교
                String[] parts1 = o1.split(" ");
                String[] parts2 = o2.split(" ");

                int num1 = 0;
                int num2 = 0;

                if (parts1.length > 1) {
                    num1 = Integer.parseInt(parts1[1]);
                }

                if (parts2.length > 1) {
                    num2 = Integer.parseInt(parts2[1]);
                }

                return Integer.compare(num2, num1);
            }
        };
        
        Arrays.sort(lines, comparator);
        int count = 0;
        for (String l: lines) { 
        	if(count == 10) { //점수는 최대 10개 저장
        		break;
        	}
        	save(l, "scores.txt");
        	count ++;
        }
	}
	
	// 파일에 데이터 쓰기
		public static void save(String data, String file) {
			try {
				PrintWriter out = new PrintWriter(new FileWriter(file, true));
				out.print(data+"\n");
				out.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		
		public static void clearFile(String file) {
			try {
				PrintWriter out = new PrintWriter(new FileWriter(file, false));
				out.print("");
				out.close();
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		

}
