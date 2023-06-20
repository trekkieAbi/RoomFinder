package com.room.finder;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestList {
    @Test
    public void testList_ReturnsSingle_value(){
        List mockList=mock(List.class);
        when(mockList.size()).thenReturn(1);

        assertEquals(1,mockList.size());
        assertEquals(1,mockList.size());


    }
}
