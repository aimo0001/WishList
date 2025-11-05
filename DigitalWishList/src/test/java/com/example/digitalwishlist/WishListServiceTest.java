package com.example.digitalwishlist;

import com.example.digitalwishlist.Model.WishList;
import com.example.digitalwishlist.Repository.WishListRepository;
import com.example.digitalwishlist.Service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class WishListServiceTest {
    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private WishListService wishListService;

    private WishList wishlist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        wishlist = new WishList();
        wishlist.setId(1L);
        wishlist.setTitle("Birthday List");
    }

    @Test
    void testFindAll() {
        when(wishListRepository.findAll()).thenReturn(Arrays.asList(wishlist));

        List<WishList> result = wishListService.findAll();

        assertEquals(1, result.size());
        assertEquals("Birthday List", result.get(0).getTitle());
    }

    @Test
    void testFindById() {
        when(wishListRepository.findById(1L)).thenReturn(Optional.of(wishlist));

        WishList result = wishListService.findById(1L);

        assertNotNull(result);
        assertEquals("Birthday List", result.getTitle());
    }

    @Test
    void testSave() {
        when(wishListRepository.save(wishlist)).thenReturn(wishlist);

        WishList result = wishListService.save(wishlist);

        assertNotNull(result);
        assertEquals("Birthday List", result.getTitle());
        verify(wishListRepository, times(1)).save(wishlist);
    }

    @Test
    void testDelete() {
        wishListService.delete(1L);
        verify(wishListRepository, times(1)).deleteById(1L);
    }
}
