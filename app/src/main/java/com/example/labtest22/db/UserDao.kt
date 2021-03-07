package com.demo.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.demo.roomdemo.db.UserEntity
@Dao
interface UserDao {


    @Query("SELECT * FROM UserEntity ORDER BY title")
    fun loadAll(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM UserEntity ORDER BY id DESC")
    fun getAllUserInfo(): List<UserEntity>?

    @Query("SELECT * FROM UserEntity WHERE id = :bookmarkId")
    fun loadBookmark(bookmarkId: Long): UserEntity

    @Query("SELECT * FROM UserEntity WHERE id = :bookmarkId")
    fun loadLiveBookmark(bookmarkId: Long): LiveData<UserEntity>

    @Insert
    fun insertUser(user: UserEntity?): Long

    @Delete
    fun deleteUser(user: UserEntity?)

    @Update
    fun updateUser(user: UserEntity?)

}