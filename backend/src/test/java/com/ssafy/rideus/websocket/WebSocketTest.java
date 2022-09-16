package com.ssafy.rideus.websocket;

import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketTest {

//    @LocalServerPort
//    private int port;
//
//    @MockBean
//    private RideService rideService;
//
//    @MockBean
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private WebSocketStompClient webSocketStompClient;
//
//    private StompSession session;
//
//    private BlockingQueue<String> messageResponses;
//
//    @BeforeEach
//    void setUp() {
//        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
//        WebSocketTransport webSocketTransport = new WebSocketTransport(standardWebSocketClient);
//        List<Transport> transports = singletonList(webSocketTransport);
//        SockJsClient sockJsClient = new SockJsClient(transports);
//        webSocketStompClient = new WebSocketStompClient(sockJsClient);
//        webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
//        messageResponses = new LinkedBlockingDeque<>();
//    }
//
//    @Test
//    @DisplayName("현재 내 위치 전송")
//    void myCurrentPosition() throws Exception {
//        // given
//        given(jwtUtil.getSubject(any()))
//                .willReturn("1");
//        given(rideService.searchMemberInfo(any()))
//                .willReturn(TEST_CURRENT_POSITION_RESPONSE);
//
//        // when
//        StompHeaders stompHeaders = new StompHeaders();
//        stompHeaders.add(HttpHeaders.AUTHORIZATION, TEST_AUTHORIZATION);
//        session = webSocketStompClient
//                .connect("ws://localhost:" + port + "/api/ws-stomp", null, stompHeaders, new StompSessionHandlerAdapter() {
//                }, new Object[0]).get(60, TimeUnit.SECONDS);
//
//        stompHeaders = new StompHeaders();
//        stompHeaders.add(StompHeaders.DESTINATION, "/sub/ride/room/" + TEST_CURRENT_POSITION_REQUEST.getRideRoomId());
//        stompHeaders.add(HttpHeaders.AUTHORIZATION, TEST_AUTHORIZATION);
//        session.subscribe(stompHeaders, new StompFrameHandlerImpl<>(messageResponses));
//
//        stompHeaders = new StompHeaders();
//        stompHeaders.add(StompHeaders.DESTINATION, String.format("/pub/ride/group"));
//        stompHeaders.add(HttpHeaders.AUTHORIZATION, TEST_AUTHORIZATION);
//        session.send(stompHeaders, TEST_CURRENT_POSITION_REQUEST);
//
//        String connect = messageResponses.poll(5, TimeUnit.SECONDS);
//        GroupRiddingResponse result = objectMapper.readValue(connect, GroupRiddingResponse.class);
//
//        // then
//        assertThat(result.getLat()).isEqualTo(TEST_CURRENT_POSITION_REQUEST.getLat());
//        assertThat(result.getLng()).isEqualTo(TEST_CURRENT_POSITION_REQUEST.getLng());
//        assertThat(result.getNickname()).isEqualTo("pjk");
//        assertThat(result.getMemberId()).isEqualTo(1L);
//
//        then(rideService).should(times(1)).searchMemberInfo(any());
//    }
//
//
//    static class StompFrameHandlerImpl<T> implements StompFrameHandler {
//
//        private ObjectMapper objectMapper;
//        private BlockingQueue<String> messageResponses;
//
//        public StompFrameHandlerImpl(BlockingQueue<String> messageResponses) {
//            this.objectMapper = new ObjectMapper();
//            this.messageResponses = messageResponses;
//        }
//
//        // payload 를 받을 클래스 타입을 지정
//        @Override
//        public Type getPayloadType(StompHeaders headers) {
//            return byte[].class;
//        }
//
//        // payload 가 담길 BlockingQueue 지정
//        @SneakyThrows
//        @Override
//        public void handleFrame(StompHeaders headers, Object payload) {
//            String content = new String((byte[]) payload);
//            messageResponses.offer(content);
//        }
//    }
}
