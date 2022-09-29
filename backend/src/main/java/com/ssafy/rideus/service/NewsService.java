package com.ssafy.rideus.service;

import com.ssafy.rideus.dto.news.NewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    public List<NewsResponseDto> getNews() throws IOException {

        List<NewsResponseDto> result = new ArrayList<>();
        Document doc = Jsoup.connect("https://search.naver.com/search.naver?where=news&sm=tab_jum&query=%EC%9E%90%EC%A0%84%EA%B1%B0").get();
        Elements newsList = doc.select("div[id=wrap]").select("div[id=container]").select("div[id=content]").select("div[id=main_pack]").select("section[class=sc_new sp_nnews _prs_nws]").select("div[class=api_subject_bx]").select("div[class=group_news]").select("ul[class=list_news]").select("li[class=bx]");

        for (int i=0; i<5; i++) {
            //기사 제목
            String title = newsList.get(i).select("div[class=news_wrap api_ani_send]").select("div[class=news_area]").select("a[class=news_tit]").attr("title");
            //기사 링크
            String link = newsList.get(i).select("div[class=news_wrap api_ani_send]").select("div[class=news_area]").select("a[class=news_tit]").attr("href");
            //기사 사진 url
            String type = newsList.get(i).select("div[class=news_wrap api_ani_send]").select("a[class=dsc_thumb]").attr("class");
            String image = "";
            if (type.equals("dsc_thumb ")) {
                image = newsList.get(i).select("div[class=news_wrap api_ani_send]").select("a[class=dsc_thumb]").select("img").attr("src");
            } else {
                image = newsList.get(i).select("div[class=news_wrap api_ani_send]").select("a[class=dsc_thumb type_video]").select("img").attr("src");
            }

            NewsResponseDto newsResponseDto = NewsResponseDto.builder()
                    .title(title)
                    .link(link)
                    .image(image)
                    .build();
            result.add(newsResponseDto);

        }

        return result;
    }

}
