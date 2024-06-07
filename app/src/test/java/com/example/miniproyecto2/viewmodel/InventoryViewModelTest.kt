package com.example.miniproyecto2.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.miniproyecto2.model.Item
import com.example.miniproyecto2.model.SearchCriteria
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
                "1", "Cargador", "Cargador de cel", 15000.0,
                3, "image", "Tecnologia")
        )
        Mockito.`when`(inventoryRepository.getItems()).thenReturn(mockItems)

        //when
        inventoryViewModel.getItems()

        //then
        assertEquals(inventoryViewModel.items.value, mockItems)
    }

    @Test
    fun `test editItem method`() = runBlocking {
        // Given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val id = "1"
        val name = "Test Item"
        val description = "Test Description"
        val price = 100.0
        val quantity = 10
        val category = "Test Category"
        val image = "Test Image"

        val item = Item(id, name, description, price, quantity, image, category)

        Mockito.`when`(imageResponseRepository.getImage(name)).thenReturn(image)

        // When
        inventoryViewModel.editItem(id, name, description, price, quantity, category)

        // Then
        Mockito.verify(inventoryRepository).editItem(item)
    }

    @Test
    fun `test metodo addItem`() = runBlocking{
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val id = ""
        val name = "Sofa con un nombre muy extrano"
        val image = "imagen random"
        val des = "Mueble muy comodo"
        val price = 15000.0
        val quantity = 3
        val category = "Muebles"

        Mockito.`when`(imageResponseRepository.getImage(name)).thenReturn(image)

        //when
        val item = Item(id,name,des,price,quantity,image,category)
        inventoryViewModel.addItem(name,des,price,quantity,category)

        //verify
        Mockito.verify(inventoryRepository).addItem(item)
    }

    @Test
    fun `test metodo deleteItem`() = runBlocking{
        //given
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val id = ""
        val name = "Sofa con un nombre muy extrano"
        val image = "imagen random"
        val des = "Mueble muy comodo"
        val price = 15000.0
        val quantity = 3
        val category = "Muebles"

        val item = Item(id,name,des,price,quantity,image,category)

        //when
        inventoryViewModel.deleteItem(item)

        //verify
        Mockito.verify(inventoryRepository).deleteItem(item)
    }
}