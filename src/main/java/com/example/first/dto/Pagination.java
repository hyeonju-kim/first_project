package com.example.first.dto;

import lombok.Data;

@Data
public class Pagination {
    private int listSize = 10;                // 한 페이지당 보여질 리스트의 개수 . 초기값으로 목록개수를 10으로 셋팅
    private int rangeSize = 10;            //한 페이지 범위에 보여질 페이지의 개수. 초기값으로 페이지범위를 10으로 셋팅
    private int currentPage; // 현재 목록의 페이지 번호
    private int range; // 각 페이지 범위의 시작 번호 . 1~10 이면 range = 1, 11~20 이면 range = 2..
    private int listCnt; // 전체 개시물의 개수
    private int pageCnt; // 전체 페이지 범위의 개수
    private int startPage; // 각 페이지 범위 시작 번호
    private int startList;
    private int endPage; // 각 페이지 범위 끝 번호
    private boolean prev; // 이전 페이지 존재 여부
    private boolean next; // 다음 페이지 존재 여부


    public void pageInfo(int currentPage, int range, int listCnt) { // 현재페이지 1, range 1, 총게시물 수 100개
        this.currentPage = currentPage;
        this.range = range;
        this.listCnt = listCnt;

        //전체 페이지수
        this.pageCnt = (int) Math.ceil(listCnt/listSize);

        //시작 페이지
        this.startPage = (range - 1) * rangeSize + 1 ; // (1-1)* 10 + 1 = 1

        //끝 페이지
        this.endPage = range * rangeSize; // 1 * 10 = 10

        //게시판 시작번호
        this.startList = (currentPage - 1) * listSize; // (1-1) * 10 = 0

        //이전 버튼 상태
        this.prev = range == 1 ? false : true;

        //다음 버튼 상태
        this.next = endPage > pageCnt ? false : true;

        if (this.endPage > this.pageCnt) { // 마지막 페이지가 전체 페이지 수보다 크면 다음 버튼 false
            this.endPage = this.pageCnt;
            this.next = false;
        }
    }
}
