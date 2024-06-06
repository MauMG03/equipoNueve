package com.example.miniproyecto2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.repository.ImageResponseRepository
import com.example.miniproyecto2.repository.InventoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class InventoryViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var inventoryViewModel: InventoryViewModel
    private lateinit var inventoryRepository: InventoryRepository
    private  lateinit var imageResponseRepository: ImageResponseRepository
    @Before
    fun setUp() {
        inventoryRepository = Mockito.mock(InventoryRepository::class.java)
        imageResponseRepository = Mockito.mock(ImageResponseRepository::class.java)
        inventoryViewModel = InventoryViewModel(inventoryRepository, imageResponseRepository)
    }

    @Test
    fun `test metodo getItems`() = runBlocking{
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val mockItems = mutableListOf(
            Item(
                "Cargador", "Cargador de cel", 15000.0,
                3, "image", "Tecnologia")
        )
        Mockito.`when`(inventoryRepository.getItems()).thenReturn(mockItems)

        //when
        inventoryViewModel.getItems()

        //then
        assertEquals(inventoryViewModel.items.value, mockItems)
    }
}