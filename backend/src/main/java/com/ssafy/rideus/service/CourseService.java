package com.ssafy.rideus.service;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.mongo.CourseCoordinateRepository;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class CourseService {
	
	//	private final MemberRepository memberRepository;
	@Autowired
	private CourseCoordinateRepository courseCoordinateRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	
	// 추천 코스 리스트 조회
	public List<Course> findAllCourses() {
		
		List<Course> courses = courseRepository.findAll();
		
		return courses;
	}
	
	
	// 추천 코스 상세 조회
	public CourseCoordinate findCourse(String courseId) {
		
		CourseCoordinate courseDetail = courseCoordinateRepository.findById(courseId)
				.orElseThrow(() -> new NotFoundException("코스 상세 조회 실패"));
		// CourseCoordinate courseDetail = courseCoordinateRepository.findById(coureId).get();
		
		return courseDetail;
	}
	
	
	// 코스 검색
	
	
	
	// 코스 추가
	
	
	/////////////////////////////////////////////////////////////////////	
	// 추천 코스 크롤링 데이터 넣기
	// 배열 
	public void addCrawlingData() {
		
		try { 
			// 1. 트렉 홈페이지에서 코스 명, 코스 거리 데이터 크롤링하기
			String[][] additionalData = getAdditionalData();
			
			
			// 2. Gpx File 읽기
			
//            import org.w3c.dom.Document;
//            import org.w3c.dom.Element;
//            import org.w3c.dom.Node;
//            import org.w3c.dom.NodeList;
//            import org.xml.sax.SAXException;
			
			
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        
	        String filePath = "C:\\Users\\SSAFY\\Desktop\\RideUs\\course-crawling\\gpx-files\\";
	        int fileName = 1;
//	        int fileCount = 1;
	        // 136: 크롤링 데이터 이상, 137~141 : 중복된 코스
	        int fileCount = 135;
	        String type = ".gpx";
	        
	        for(; fileName <= fileCount; fileName++) {
	        	
	            // .gpx 파일 열기
	            File gpxFile = new File(filePath + fileName + type);
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            org.w3c.dom.Document doc = dBuilder.parse(gpxFile);
	            
	            org.w3c.dom.Element root = doc.getDocumentElement();
	            Node firstNode = root.getFirstChild();
	            Node metadata = firstNode.getNextSibling();
	            NodeList childList = metadata.getChildNodes();
//	            for(int i=0; i<childList.getLength(); i++) {
//	            	Node item = childList.item(i);
//	            	System.out.println("idx : ");
//	            	if(item.getNodeType() == Node.ELEMENT_NODE) { // 노드의 타입이 Element일 경우(공백이 아닌 경우)
//	            		System.out.println(item.getNodeName());
//	            		System.out.println(item.getTextContent());
//	            	} else {
//	            		System.out.println("공백 입니다.");
//	            	}
//	            }
	            Node link = childList.item(3);
	            String courseURL = link.getAttributes().getNamedItem("href").getTextContent();
	            
            
	            
	            // trk tag 파싱
	            NodeList trk = doc.getElementsByTagName("trk");
	
	            NodeList childNodes = doc.getChildNodes();
	//            System.out.println("[Child Node]");
	//            for(int i=0; i< childNodes.getLength(); i++) {
	//                System.out.println("Node Name : " + childNodes.item(i));
	//            }
	
	            // metadata, trk tag
	            NodeList bigChilds = childNodes.item(0).getChildNodes();
	//            for(int i=0; i< bigChilds.getLength(); i++) {
	//                System.out.println("Node Name : " + bigChilds.item(i));
	//            }
	
	            Node trkNode = bigChilds.item(3); // trk tag
	
	            // name, trkseg tag
	            NodeList trkList = trkNode.getChildNodes();
	            // 주행 코스의 name 을 저장
	            Node name = trkList.item(1);
	
	            
	            String[] trkName = name.getTextContent().split("- ");
	            String gpxCourseName = trkName[1].trim();
	            
        
	            NodeList trkptList = doc.getElementsByTagName("trkpt");
	            
	            
	            // 코스 좌표 리스트 생성
	            List<Coordinate> coordinates = getCourseCoordinates(trkptList);
	            // 체크포인트 좌표 리스트 생성
	            List<Coordinate> checkpoints = getCheckpoints(trkptList);
	            
	            // 코스 좌표, 체크포인트 좌표 데이터 MongoDB에 넣기
	            CourseCoordinate courseCoordinate = courseCoordinateRepository.save(CourseCoordinate.create(coordinates, checkpoints));
//	            System.out.println(courseCoordinate.getId());
          
	            
	            // gpx 파일 코스와 일치하는 크롤링한 코스 정보 찾기    해당 정보는 새로운 Course 객체 생성 시 사용
	            String[] courseAdditionalData = searchCourse(courseURL, additionalData);
//	            System.out.println("courseName: "+gpxCourseName+" // distanceInfo: "+courseAdditionalData[2]);
	            
    		
	            // 코스 관련 요약 정보 MySQL에 넣기
	        	// (식별자(MongDB 식별자랑 동일), 코스명, 코스 길이, 시작 지점, 죵료 지점, 예상 주행 시간, 좋아요 개수, 사진 url, 회원번호)
	            String courseName = gpxCourseName;
	            String distanceInfo = courseAdditionalData[2];
	            
	            Coordinate startCoordinate = checkpoints.get(0);
	            Coordinate finishCoordinate = checkpoints.get(checkpoints.size()-1);
	    		String start = locAPI(startCoordinate.getLng(), startCoordinate.getLat());
	    		String finish = locAPI(finishCoordinate.getLng(), finishCoordinate.getLat());
	            
	            String expectedTime = courseAdditionalData[3];
	            // 서버 db 확인하고 member_id 값 수정하기
	            courseRepository.save(new Course(courseCoordinate.getId(), courseName, distanceInfo, start, finish, expectedTime, 0, null, null));
	            System.out.println();



	        }
	            
	            
		} catch (Exception e) {
			System.out.println(">>> addCrawlingData() Exception: "+e);
		}
	}
	
	
	// ___추천 코스 크롤링 데이터 넣을 때 필요한 메서드들___
	
	
	// 트렉 홈페이지에서 코스명, 코스 길이 크롤링 + 코스 길이로 예상 소요 시간 계산
	private static String[][] getAdditionalData() {
		// 전체 코스 개수 (gpx 파일 개수)
		int fileCount = 141;

		// 0: 코스 링크, 1: 코스 명, 2: 코스 길이, 3: 예상 소요 시간
		String[][] courseInfoArr = new String[fileCount][4];
		String[] city = {"seoul", "busan", "gyeonggi", "daejeon"};
		
		int idx = 0;
		
		//★★★ i<4
		for(int i=0; i<4; i++) {
			String url = "https://www.trekbikes.com/kr/ko_KR/greatrides/"+city[i];
			Document doc;
			try {
				doc = Jsoup.connect(url).header("Content-Type", "application/json;charset=UTF-8").get();
				// 해당 화면의 소스 코드 출력
//				System.out.println(doc);
				Elements courseBlocks = doc.select("div.editorial-small__content");
				// 코스 소스 코드 출력
//				System.out.println(courseBlocks);
				for( Element courseBlock : courseBlocks ) {
					String courseURL = ""; // 코스 링크
					String courseTitle = ""; // 코스명
					String[] courseInfo = null; // 코스 정보
					String courseDistance = ""; // 거리
					
					courseURL = courseBlock.select("div.editorial-small__content > a").attr("href");
					
					courseTitle = courseBlock.select("h4.editorial-small__header").text();
					// couseTitle 트렉으로 시작하면(코스가 아니라 대리점임) 데이터 정제 중지
					if(courseTitle.contains("트렉")) {
						break;
					}
//					System.out.println(">>> 코스명 : "+courseTitle);
					
					courseInfo = courseBlock.select("p.text-weak").text().split(" ");
					courseDistance = courseInfo[1];
//					System.out.println(">>> 코스명: "+courseTitle+" 코스 거리: "+courseDistance);
					
					// 0: 코스 링크, 1: 코스 명, 2: 코스 길이, 3: 예상 소요 시간
					courseInfoArr[idx][0] = courseURL;
					courseInfoArr[idx][1] = courseTitle;
					courseInfoArr[idx][2] = courseDistance;
					courseInfoArr[idx][3] = calExpectedTime(courseDistance);
					
					
					idx++;
				}
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(">>> Exception: " + e);
			}
			
		}
		
		return courseInfoArr;		
	}
	
	
	// 예상 소요 시간(분) 구하기 (distance: 거리 숫자, 거리 단위 km, 시속 25km로 구하기☆★☆)
	private static String calExpectedTime(String distance) {
		
		// 1분 속도
		Double vel = 25.0 / 60.0;
		// 소수점 아래 둘째자리까
		Double v = Math.round(vel*100)/100.0;
		
		try {
			int expectedTime = (int) Math.ceil((Double.parseDouble(distance) / v));
			
			return Integer.toString(expectedTime);
		} catch(Exception e) {
			System.out.println(">>> calExpectedTime Exception : "+e);
			return "";
		}

	}
	
	
	// gpx 파일에서 코스 좌표 데이터 가져오기
	private static List<Coordinate> getCourseCoordinates(NodeList coordinateList) {
		List<Coordinate> coordinates = new ArrayList<>();
		try {
			for(int index=0; index<coordinateList.getLength(); index++) {
            	
                Node node = coordinateList.item(index);

                Node lat = node.getAttributes().getNamedItem("lat");
                Node lon = node.getAttributes().getNamedItem("lon");

                String latstr = lat.getTextContent();
                String lonstr = lon.getTextContent();
//              System.out.println(lonstr + ", " + latstr);
                
                Coordinate coordinate = new Coordinate(latstr, lonstr);
                coordinates.add(coordinate);
            } // end of for

            return coordinates;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(">>> getCourseCoordinates() Exception: "+e);
			return coordinates;
		}
	}


	// gpx 파일의 코스 좌표로 체크포인트 리스트 생성
	// 총 좌표 개수를 4등분해서 시작점, 종료점 포함 5개의 점 저장
	private static List<Coordinate> getCheckpoints(NodeList coordinateList) {
		List<Coordinate> checkpoints = new ArrayList<>();
		
        int coordinateCnt = coordinateList.getLength();
//        System.out.println(coordinateCnt);
        
        
        for(int i=0; i<4; i++) {
        	
        	String latstr = coordinateList.item((coordinateCnt/4)*i).getAttributes().getNamedItem("lat").getTextContent();
            String lonstr = coordinateList.item((coordinateCnt/4)*i).getAttributes().getNamedItem("lon").getTextContent();
//          System.out.println(lonstr + ", " + latstr);
            
            Coordinate coordinate = new Coordinate(latstr, lonstr);
            checkpoints.add(coordinate);
        }
        
        String lat = coordinateList.item(coordinateCnt-1).getAttributes().getNamedItem("lat").getTextContent();
        String lon = coordinateList.item(coordinateCnt-1).getAttributes().getNamedItem("lon").getTextContent();
        Coordinate coordinate = new Coordinate(lat, lon);
        checkpoints.add(coordinate);
        
        return checkpoints;
	}

	
	// kakao api 이용하여 좌표로 해당 주소(~동) 찾기 (시작 지점, 종료 지점  데이터 저장할 때 사용)
	// 참고 https://gaebalsaebal-developer.tistory.com/3
	private static String locAPI(String lng, String lat) throws URISyntaxException {
		
		String addressName = "";
		
		// "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=127.1086228&y=37.4012191"
		// Authorization: KakaoAK ${REST_API_KEY}

		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
        // REST_API_KEY 
		final String restApiKey = "KakaoAK b834a23c18a62707b7f97e56c265b1ea";
		headers.set("Authorization", restApiKey);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		// x: 경도 longitude, y: 위도 latitude
		String apiURL = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x="+lng+"&y="+lat;
		URI uri = new URI(apiURL);             
		
		ResponseEntity<String> res = rest.exchange(uri, HttpMethod.GET, entity, String.class);
		
		try {
			JSONObject locJsonObj1 = new JSONObject(res.getBody());
			JSONArray locJsonArr = new JSONArray(locJsonObj1.getString("documents"));
			JSONObject locJsonObj2 = (JSONObject) locJsonArr.get(0);
			addressName = locJsonObj2.getString("address_name");
			
			return addressName;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(">>> locAPI Exception: "+e);
			return addressName;
		}
	}
	

	// gpx 파일 데이터와 트렉 홈페이지 크롤링 코스 데이터 매칭
    public static String[] searchCourse(String courseURL, String[][] additionalData) {
    	int fileCount = additionalData.length;
    	for(int i=0; i<fileCount; i++) {
    		if(additionalData[i][0].equals(courseURL)) {
    			return additionalData[i];
    		}
    	}
    	System.out.println("코스 데이터 매칭 안 됨");
    	return null;
    }	
	
	
	
	
	
	
	// 테스트
//	public static void main(String[] args) {
//		try {
////			calExpectedTime("35.1");
////			addCrawlingData();
////			getAdditionalData();
////			locAPI("127.1086228", "37.4012191");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println(">>> test Exception: "+e);
//		}
//	}
	
	
}
