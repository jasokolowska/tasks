package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloListDto> lists = new ArrayList<>();
        lists.add(new TrelloListDto("1", "list1", true));
        lists.add(new TrelloListDto("2", "list2", false));
        lists.add(new TrelloListDto("3", "list3", true));

        List<TrelloBoardDto> boards = new ArrayList<>();
        boards.add(new TrelloBoardDto("1", "board1", lists));

        //When
        List<TrelloBoard> result = trelloMapper.mapToBoards(boards);

        //Then
        assertEquals(1, result.size());
        assertEquals(3, result.get(0).getLists().size());
        assertTrue(result.get(0).getLists().get(0) != null);
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloList> lists = new ArrayList<>();
        lists.add(new TrelloList("1", "list1", true));
        lists.add(new TrelloList("2", "list2", false));

        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(new TrelloBoard("1", "board1", lists));
        boards.add(new TrelloBoard("2", "empty", new ArrayList<>()));

        //When
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(boards);

        //Then
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getLists().size());
        assertTrue(result.get(0).getLists().get(0) != null);
    }

}