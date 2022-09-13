package com.ssafy.rideus.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.rideus.common.dto.rideroom.response.GroupRiddingResponse;
import com.ssafy.rideus.config.security.util.JwtUtil;
import com.ssafy.rideus.service.RideService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static com.ssafy.rideus.websocket.TestInfo.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

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
