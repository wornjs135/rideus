package com.ssafy.rideus.service;

import com.ssafy.rideus.common.api.S3Upload;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDto;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDtoInterface;
import com.ssafy.rideus.dto.course.response.PopularityCourseResponse;
import com.ssafy.rideus.dto.review.ReviewStarAvgDto;
import com.ssafy.rideus.dto.review.ReviewStarAvgDtoInterface;
import com.ssafy.rideus.dto.tag.common.TagDto;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.jpa.MemberTagRepository;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ssafy.rideus.common.exception.NotFoundException;
import com.ssafy.rideus.dto.course.common.CourseReviewTagTop5DtoInterface;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDto;
import com.ssafy.rideus.dto.course.common.RecommendationCourseDtoInterface;
import com.ssafy.rideus.domain.Course;
import com.ssafy.rideus.domain.Member;
import com.ssafy.rideus.domain.Record;
import com.ssafy.rideus.domain.base.Coordinate;
import com.ssafy.rideus.domain.collection.CourseCoordinate;
import com.ssafy.rideus.repository.jpa.CourseRepository;
import com.ssafy.rideus.repository.jpa.MemberRepository;
import com.ssafy.rideus.repository.mongo.CourseCoordinateRepository;
import com.ssafy.rideus.repository.mongo.MongoRecordRepository;
import com.ssafy.rideus.repository.jpa.MemberTagRepository;
import com.ssafy.rideus.repository.jpa.RecordRepository;

@Service
@RequiredArgsConstructor
public class CourseService {
	
	private static final String SUCCESS = "success";
  private static final String FAIL = "fail";
	
	private final CourseCoordinateRepository courseCoordinateRepository;
	private final MongoRecordRepository mongoRecordRepository;
    private final CourseRepository courseRepository;
    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;
    private final MemberTagRepository memberTagRepository;

	private final S3Upload s3Upload;
    private static List<Coordinate> checkpoints = new ArrayList<>();
	
	
    public List<RecommendationCourseDto> getRecommendationCourseByTag(Long memberId) {
        List<RecommendationCourseDtoInterface> recommendationCourseByMemberId = courseRepository.getRecommendationCourseByMemberId(memberId);

        List<RecommendationCourseDto> recommendationCourseDtos = new ArrayList<>();
        for (RecommendationCourseDtoInterface r : recommendationCourseByMemberId) {

            if (recommendationCourseDtos.isEmpty()) {
                recommendationCourseDtos.add(RecommendationCourseDto.from(r));
            } else {
                RecommendationCourseDto lastCourse = recommendationCourseDtos.get(recommendationCourseDtos.size() - 1);
                if(lastCourse.getCourseId().equals(r.getCourseId())) {
                    lastCourse.addTags(r.getTagId(), r.getTagName());
                } else {
                    recommendationCourseDtos.add(RecommendationCourseDto.from(r));
                }
            }

        }

//        System.out.println(recommendationCourseDtos.size());
//        System.out.println(recommendationCourseDtos);

        return recommendationCourseDtos;
    }


	public List<RecommendationCourseDto> getPopularityCourseWithBookmark(Long memberId) {
		List<RecommendationCourseDtoInterface> popularityCourses = courseRepository.findAllOrderByLikeCountWithBookmark(memberId);

		List<RecommendationCourseDto> recommendationCourseDtos = new ArrayList<>();
		for (RecommendationCourseDtoInterface c : popularityCourses) {
			if (recommendationCourseDtos.isEmpty()) {
				recommendationCourseDtos.add(RecommendationCourseDto.from(c));
			} else {
				RecommendationCourseDto lastCourse = recommendationCourseDtos.get(recommendationCourseDtos.size() - 1);
				if(lastCourse.getCourseId().equals(c.getCourseId())) {
					lastCourse.addTags(c.getTagId(), c.getTagName());
				} else {
					recommendationCourseDtos.add(RecommendationCourseDto.from(c));
				}
			}
		}

		return recommendationCourseDtos;
	}

	public List<PopularityCourseResponse> getPopularityCourseWithBookmarkWithoutBookmark() {
		List<Course> popularityCourses = courseRepository.findAllOrderByLikeCountWithoutBookmark();

		return popularityCourses.stream().map(course -> PopularityCourseResponse.from(course)).collect(Collectors.toList());
	}
	

    
	// 코스에 대한 태그들 정리
    private Map<String, List<TagDto>> getAllCourseTagsMap(List<String> courseIds) {
    	List<CourseReviewTagTop5DtoInterface> allCourseTags;
    	if(courseIds.size() == 0) {
    		allCourseTags = courseRepository.getAllCourseTags();
    	} else {
    		allCourseTags = courseRepository.getSpecificCourseTags(courseIds);
    	}
        
        Map<String, List<TagDto>> allCourseTagsMap = new HashMap<String, List<TagDto>>();
        for(CourseReviewTagTop5DtoInterface courseTags : allCourseTags) {
        	String courseId = courseTags.getCourseId();
        	if(allCourseTagsMap.containsKey(courseId)) {
        		List<TagDto> tagList = allCourseTagsMap.get(courseId);
        		tagList.add(TagDto.from(courseTags.getTagId(), courseTags.getTagName()));
        		allCourseTagsMap.replace(courseId, tagList);
        	} else {
        		List<TagDto> tagList = new ArrayList<TagDto>();
        		tagList.add(TagDto.from(courseTags.getTagId(), courseTags.getTagName()));
        		allCourseTagsMap.put(courseTags.getCourseId(), tagList);
        	}
        }
        
        return allCourseTagsMap;
    }

	
	// 코스 리스트 조회
    public List<RecommendationCourseDto> getAllCourses(Long memberId) {
    	// 코스 정보 
    	List<RecommendationCourseDtoInterface> allCourses = courseRepository.getAllCourses(memberId);
    	
    	// 코스에 대한 태그들 
    	Map<String, List<TagDto>> allCourseTagsMap = getAllCourseTagsMap(new ArrayList<String>());
    	
    	// 코스 관련 정보 모음
        List<RecommendationCourseDto> recommendationCourseDtoList = new ArrayList<>();
        for (RecommendationCourseDtoInterface course : allCourses) {
        	recommendationCourseDtoList.add(RecommendationCourseDto.find(course, allCourseTagsMap.get(course.getCourseId())));
        }

        return recommendationCourseDtoList;
    }
    
	
    // 특정 코스 정보 조회
	public List<RecommendationCourseDto> getSpecificCourse(Long memberId, List<String> courseIds) {
		// 코스 정보
		List<RecommendationCourseDtoInterface> courses = courseRepository.getSpecificCourse(memberId, courseIds);

		
    	// 코스에 대한 태그들 
    	Map<String, List<TagDto>> allCourseTagsMap = getAllCourseTagsMap(courseIds);
		

//		CourseCoordinate courseDetail = courseCoordinateRepository.findById(courseId)
//				.orElseThrow(() -> new NotFoundException("코스 상세 조회 실패"));
//		// CourseCoordinate courseDetail = courseCoordinateRepository.findById(coureId).get();
		
        List<RecommendationCourseDto> recommendationCourseDtoList = new ArrayList<>();
        for (RecommendationCourseDtoInterface course : courses) {
        	recommendationCourseDtoList.add(RecommendationCourseDto.find(course, allCourseTagsMap.get(course.getCourseId())));
        }

        return recommendationCourseDtoList;
	}
	
	
	// RecommendationCourseDto -> Map (Dto + 추가 정보)
	private Map<String, Object> convertCourseDtoIntoMap(RecommendationCourseDto course, Double starAvg) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// Map에 담기
		resultMap.put("courseId", course.getCourseId());
		resultMap.put("courseName", course.getCourseName());
		resultMap.put("distance", course.getDistance());
		resultMap.put("expectedTime", course.getExpectedTime());
		resultMap.put("start", course.getStart());
		resultMap.put("finish", course.getFinish());
		resultMap.put("likeCount", course.getLikeCount());
		resultMap.put("imageUrl", course.getImageUrl());
		resultMap.put("category", course.getCategory());
		resultMap.put("bookmarkId", course.getBookmarkId());
		resultMap.put("starAvg", starAvg);
		resultMap.put("tags", course.getTags());
//		resultMap.put("coordinates", courseCoordinate.getCoordinates());
//		resultMap.put("checkpoints", courseCoordinate.getCheckpoints());
		
		return resultMap;
	}
	
	
	
    // 코스 상세 조회
	// 특정 코스 정보 조회 + 몽고디비 데이터 가져오기
	public Map<String, Object> getCourse(Long memberId, String courseId) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// MySQL에 있는 코스 데이터
		List<String> courseIds = new ArrayList<String>();
		courseIds.add(courseId);
		RecommendationCourseDto course = getSpecificCourse(memberId, courseIds).get(0);
		
		// 해당 코스의 리뷰 별점 통계(count, sum) 정보 가져오기
		List<ReviewStarAvgDtoInterface> starAvgDtoInterfaceList = courseRepository.getCoursesStarAvg(courseIds);
		
		// 리뷰 별점 평균
		double starAvg;
		if(starAvgDtoInterfaceList.size() > 0) {
			ReviewStarAvgDto starAvgDto = ReviewStarAvgDto.from(starAvgDtoInterfaceList.get(0));
			starAvg = Math.round((starAvgDto.getSum() / starAvgDto.getCount())*10) / 10.0;
		} else {
			starAvg = 0;
		}
		
		
		// MongoDB에 있는 데이터 가져오기
		CourseCoordinate courseCoordinate = courseCoordinateRepository.findById(courseId).get();
		
		// Map에 담기
		resultMap = convertCourseDtoIntoMap(course, starAvg);
		resultMap.put("coordinates", courseCoordinate.getCoordinates());
		resultMap.put("checkpoints", courseCoordinate.getCheckpoints());
		
		
		return resultMap;
	}
	
	
	// 코스 검색
	public List<RecommendationCourseDto> getAllCoursesByKeyword(Long memberId, String keyword) {
		
		Set<String> courseIdsSet = new HashSet<String>();
		
		List<String> courseIds1 = courseRepository.getAllCourseIdsByKeyword(keyword);
		for(int i=0; i<courseIds1.size(); i++) {
			courseIdsSet.add(courseIds1.get(i));
		}
		List<String> courseIds2 = courseRepository.getAllCourseIdsByTagKeyword(keyword);
		for(int i=0; i<courseIds2.size(); i++) {
			courseIdsSet.add(courseIds2.get(i));
		}		
		
		List<String> courseIds = new ArrayList<String>(courseIdsSet);
//		System.out.println(courseIds.toString());
		
		return getSpecificCourse(memberId, courseIds);
	}
	

	// 현 위치 기반 코스 추천
	public List<Map<String, Object>> getAllCoursesByLoc(Long memberId, Double lat, Double lng) {
		
		List<Map<String, Object>> resultMapList = new ArrayList<Map<String,Object>>();
//	public List<RecommendationCourseDto> getAllCoursesByLoc(Long memberId, Double lat, Double lng) {
		
		// 전체 좌표 빼고 나머지만 가져오는 쿼리 작성해보기  ★★★
		List<CourseCoordinate> allCourseCoordinates = courseCoordinateRepository.findAll();
		
		
		Map<String, Double> intervalMap = new HashMap<String, Double>();
//		double lat1 = Double.parseDouble(lat);
//		double lon1 = Double.parseDouble(lng);
		double lat1 = lat;
		double lon1 = lng;
		for(CourseCoordinate courseCoordinate : allCourseCoordinates) {
			double lat2 = Double.parseDouble(courseCoordinate.getCheckpoints().get(0).getLat());
			double lon2 = Double.parseDouble(courseCoordinate.getCheckpoints().get(0).getLng());
			intervalMap.put(courseCoordinate.getId(), intervalMeter(lat1, lon1, lat2, lon2));
		}
		
		// intervalMeter 기준 오름차순 정렬
		
		// Map.Entry 리스트 작성
		List<Entry<String, Double>> listEntries = new ArrayList<Entry<String, Double>>(intervalMap.entrySet());

		// 비교함수 Comparator를 사용하여 오름차순으로 정렬
		Collections.sort(listEntries, new Comparator<Entry<String, Double>>() {
			// compare로 값을 비교
			public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2) {
				// 오름 차순 정렬
				return obj1.getValue().compareTo(obj2.getValue());
			}
		});

//		System.out.println("오름 차순 정렬 결과 출력");
//		// 결과 출력
//		for(Entry<String, Double> entry : listEntries) {
//			System.out.println(entry.getKey() + " : " + entry.getValue());
//		}
//		System.out.println("_______결과 출력 끝_________");
		
		// 시작점 사이 거리 기준 오름차순으로 정렬된 코스식별자
		List<String> courseIds = new ArrayList<String>();
		// 추천할 코스 개수
		int courseCnt = 5;
		for(int i=0; i<courseCnt; i++) {
			courseIds.add(listEntries.get(i).getKey());
		}

		List<ReviewStarAvgDtoInterface> starAvgDtoInterfaceList = courseRepository.getCoursesStarAvg(courseIds);
		List<ReviewStarAvgDto> starAvgList = new ArrayList<ReviewStarAvgDto>();
		for(ReviewStarAvgDtoInterface r : starAvgDtoInterfaceList) {
			starAvgList.add(ReviewStarAvgDto.from(r));
		}
		
		
		// DB에서 가져온 코스 정보 재정렬
		List<RecommendationCourseDto> courseList = getSpecificCourse(memberId, courseIds);
		List<RecommendationCourseDto> sortedCourseList = new ArrayList<RecommendationCourseDto>();
		for(int i=0; i<courseCnt; i++) {
			String courseId = courseIds.get(i);
			for(int clIdx=0; clIdx<courseCnt; clIdx++) {
				RecommendationCourseDto course = courseList.get(clIdx);
				if(courseId.equals(course.getCourseId())) {
					sortedCourseList.add(course);
					break;
				}
			}
		}
//		System.out.println(">>>courseIds.toString() "+courseIds.toString());
//		System.out.println(">>>sortedCourseList.toString() "+sortedCourseList.toString());
		
		
		// 리뷰 별점 평균 return 값에 포함시키기
		double starAvg;
		for(int i=0; i<courseCnt; i++) {
			starAvg = 0;
			RecommendationCourseDto course = sortedCourseList.get(i);
			String courseId = course.getCourseId();
			for(int idx=0; idx<starAvgList.size(); idx++) {
				ReviewStarAvgDto starAvgDto = starAvgList.get(idx);
				if(courseId.equals(starAvgDto.getCourseId())) {
					starAvg = Math.round((starAvgDto.getSum() / starAvgDto.getCount())*10) / 10.0;
					break;
				}
			}
			
			resultMapList.add(convertCourseDtoIntoMap(course, starAvg));
		}
		
		return resultMapList;
	}
	
	
	// 코스 추가
	public String addCourseData(Map<String, String> inputMap, Long memberId, MultipartFile image) {
		// inputMap - memberId, courseName, distance, recordId

		// 기록 식별자를 받아서 좌표를 가져와서 저장
		
		
		try {
			// MongoDB에 데이터 넣기
			
			// 기록 식별자 받아서 해당 기록 좌표를 코스 좌표로 등록
			String recordId = inputMap.get("recordId");
			Record originRecord = recordRepository.findById(recordId).get();
			// 코스 좌표 리스트
			List<Coordinate> coordinates = new ArrayList<Coordinate>();
			coordinates = mongoRecordRepository.findById(recordId).get().getCoordinates();
			
			// 체크포인트 좌표 리스트 생성
			List<Coordinate> checkpoints = getCheckpoints(coordinates);
			
			// 코스 좌표, 체크포인트 좌표 데이터 MongoDB에 넣기
			CourseCoordinate courseCoordinate = courseCoordinateRepository.save(CourseCoordinate.create(coordinates, checkpoints));
			String courseId = courseCoordinate.getId();
			

			// MySQL에 데이터 넣기
			
	    	// (식별자(MongDB 식별자랑 동일), 코스명, 코스 길이, 시작 지점, 죵료 지점, 예상 주행 시간, 좋아요 개수, 사진 url, 회원번호, 리뷰리스트?, 코스태그)
	        String courseName = inputMap.get("courseName");
	        Double distance = originRecord.getRecordDistance();
	        String distanceStr = String.valueOf(distance);

	        Coordinate startCoordinate = checkpoints.get(0);
	        Coordinate finishCoordinate = checkpoints.get(checkpoints.size()-1);
			String start = locAPI(startCoordinate.getLng(), startCoordinate.getLat());
			String finish = locAPI(finishCoordinate.getLng(), finishCoordinate.getLat());
	        
			int expectedTime = Integer.parseInt(calExpectedTime(distanceStr));

			// 이미지 s3에 업로드
			String imageUrl = s3Upload.uploadImageToS3(image);

			// 서버 db 확인하고 member_id 값 수정하기
			Member member = memberRepository.findById(memberId).get();
			Course course = new Course(courseId, courseName, distance, start, finish, expectedTime, 0, imageUrl, null, member, null, null);
//	        courseRepository.save(new Course(courseId, courseName, distance, start, finish, expectedTime, 0, null, null, null));
	        courseRepository.save(course);

	        // 저장한 코스를 record에 course 정보 담아서 저장(update)
	        Record record = Record.findCourse(originRecord, course);
	        recordRepository.save(record);
	        
	        return SUCCESS;
			
		} catch (Exception e) {
			System.out.println(">>> addCourseData() Exception: "+e);
			return FAIL;
		}
		
	}
	
	
	
	
	/////////////////////////////////////////////////////////////////////	
	// 추천 코스 크롤링 데이터 넣기
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
	            Node link = childList.item(3);
	            String courseURL = link.getAttributes().getNamedItem("href").getTextContent();
	            
            
	            
	            // trk tag 파싱
	            NodeList trk = doc.getElementsByTagName("trk");
	
	            NodeList childNodes = doc.getChildNodes();
	
	            // metadata, trk tag
	            NodeList bigChilds = childNodes.item(0).getChildNodes();
	
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
	            
	            // 7m 단위 추가 좌표 생성
//	            List<Coordinate> coor = addCourseCoordinates(coordinates);
	            
	            // 체크포인트 좌표 리스트 생성
	            getCheckpoints(coordinates);
	            

	            // 코스 좌표, 체크포인트 좌표 데이터 MongoDB에 넣기
	            CourseCoordinate courseCoordinate = courseCoordinateRepository.save(CourseCoordinate.create(coordinates, checkpoints));
	            String courseId = courseCoordinate.getId();
          
	            // gpx 파일 코스와 일치하는 크롤링한 코스 정보 찾기    해당 정보는 새로운 Course 객체 생성 시 사용
	            String[] courseAdditionalData = searchCourse(courseURL, additionalData);
//	            System.out.println("courseName: "+gpxCourseName+" // distanceInfo: "+courseAdditionalData[2]);
	            
	            
	            // 코스 관련 요약 정보 MySQL에 넣기
	        	// (식별자(MongDB 식별자랑 동일), 코스명, 코스 길이, 시작 지점, 죵료 지점, 예상 주행 시간, 좋아요 개수, 사진 url, 회원번호)
	            String courseName = gpxCourseName;
	            double distance = Math.round(Double.parseDouble(courseAdditionalData[2])*100)/100.0;

	            Coordinate startCoordinate = checkpoints.get(0);
	            Coordinate finishCoordinate = checkpoints.get(checkpoints.size()-1);
	    		String start = locAPI(startCoordinate.getLng(), startCoordinate.getLat());
	    		String finish = locAPI(finishCoordinate.getLng(), finishCoordinate.getLat());
	            
	    		checkpoints.clear();
	    		int expectedTime = Integer.parseInt(courseAdditionalData[3]);
	            
	            // 서버 db 확인하고 member_id 값 수정하기
//	            courseRepository.save(new Course(courseId, courseName, distance, start, finish, expectedTime, 0, null, null, null));
	            courseRepository.save(new Course(courseId, courseName, distance, start, finish, expectedTime, 0, null, null, null, null, null));
	        }
	            
	            
		} catch (Exception e) {
			System.out.println(">>> addCrawlingData() Exception: "+e);
		}
	}
	
	
	// 트렉 홈페이지에서 코스명, 코스 길이 크롤링 + 코스 길이로 예상 소요 시간 계산
	private String[][] getAdditionalData() {
		// 전체 코스 개수 (gpx 파일 개수)
		int fileCount = 141;

		// 0: 코스 링크, 1: 코스 명, 2: 코스 길이, 3: 예상 소요 시간
		String[][] courseInfoArr = new String[fileCount][4];
		String[] city = {"seoul", "busan", "gyeonggi", "daejeon"};
		
		int idx = 0;
		
		for(int i=0; i<4; i++) {
			String url = "https://www.trekbikes.com/kr/ko_KR/greatrides/"+city[i];
			Document doc;
			try {
				doc = Jsoup.connect(url).header("Content-Type", "application/json;charset=UTF-8").get();
				Elements courseBlocks = doc.select("div.editorial-small__content");
				
				for( Element courseBlock : courseBlocks ) {
					String courseURL = ""; // 코스 링크
					String courseTitle = ""; // 코스명
					String[] courseInfo = null; // 코스 정보
					String courseDistance = ""; // 거리
					
					courseURL = courseBlock.select("div.editorial-small__content > a").attr("href");
					
					courseTitle = courseBlock.select("h4.editorial-small__header").text();
					// couseTitle 트렉으로 시작하면 코스가 아니라 대리점
					if(courseTitle.contains("트렉")) {
						break;
					}
					
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
				System.out.println(">>> getAdditionalData() Exception: " + e);
			}
		}
		return courseInfoArr;		
	}
	
	
	// 예상 소요 시간(분) 구하기 (distance: 거리 숫자, 거리 단위 km, 시속 25km)
	private static String calExpectedTime(String distance) {
		
		// 1분 속도
		Double vel = 25.0 / 60.0;
		// 소수점 아래 둘째자리까지
		Double v = Math.round(vel*100)/100.0;
		
		try {
			int expectedTime = (int) Math.ceil((Double.parseDouble(distance) / v));
			
			return Integer.toString(expectedTime);
		} catch(Exception e) {
			System.out.println(">>> calExpectedTime() Exception : "+e);
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
	// 좌표 간 거리를 구해 특정 거리(standard) 될 때마다 해당 좌표를 체크포인트로 추가
	private static List<Coordinate> getCheckpoints(List<Coordinate> coordinateList) {
		
		
		// 시작 좌표를 체크포인트에 추가
		checkpoints.add(coordinateList.get(0));
		
		double distSum = 0;
		double dist;
		
		// 체크 포인트 생성 기준 거리 1000m(1km)
//		double standard = 1000;
		int listIdx = coordinateList.size()-1;
		for(int i=0; i<listIdx; i++) {
			Coordinate coordinate1 = coordinateList.get(i);
			Coordinate coordinate2 = coordinateList.get(i+1);
			
			dist = intervalMeter(Double.parseDouble(coordinate1.getLat()), Double.parseDouble(coordinate1.getLng()), Double.parseDouble(coordinate2.getLat()), Double.parseDouble(coordinate2.getLng()));
//			System.out.println(i+". dist: "+dist);
			distSum += dist;
//			System.out.println("distSum: "+distSum);
			
			// 좌표 간 거리 합이 standard보다 커지면 그 전 좌표를 체크포인트로 추가
			if(distSum > 1000) {
				checkpoints.add(coordinate1);
				// 초기화
				distSum = 0;
				i--;
			}
		}
		
		// 종료 좌표를 체크포인트에 추가
		checkpoints.add(coordinateList.get(listIdx));
//		System.out.println(checkpoints.toString());
        
        return checkpoints;
	}
	
	
	// 두 좌표 간 거리 (단위: meter)
    public static double intervalMeter(double lat1, double lon1, double lat2, double lon2) {
        
    	if(lat1 == lat2 && lon1 == lon2) {
    		return 0;
    	}
    	
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
         
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        
        // dist 단위 meter로 하려고
        dist = dist * 1609.344;
        
        // 소수점 둘째자리까지 만들어서 반환
        // 반경 4km로 체크포인트 잡고 그 기준으로 주변 정보 카테고리화할 거라 내림(floor)함
        dist = Math.floor(dist*100)/100.0;
        
        return dist;
    }
     
    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
	

	// kakao api 이용하여 좌표로 해당 주소(~동) 찾기 (시작 지점, 종료 지점  데이터 저장할 때 사용)
	private static String locAPI(String lng, String lat) throws URISyntaxException {
		
		String addressName = "";
		
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
			System.out.println(">>> locAPI() Exception: "+e);
			return addressName;
		}
	}
	

	// gpx 파일 데이터와 트렉 홈페이지 크롤링 데이터 매칭
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

}
