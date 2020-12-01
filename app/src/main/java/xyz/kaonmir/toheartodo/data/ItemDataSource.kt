//package xyz.kaonmir.toheartodo.data
//
//import android.content.Context
//import android.util.Log
//import xyz.kaonmir.toheartodo.data.model.Book
//import xyz.kaonmir.toheartodo.data.model.Item
//import java.io.*
//import java.io.File.separator
//
//class ItemDataSource(context: Context) {
//    private val directory = context.filesDir
//    private val file = File(directory, "items.txt")
//
//    fun getItems(): ArrayList<Item> {
//        val items = ArrayList<Item>()
//
//        try {
//            val buf = BufferedReader(FileReader(file))
//            var line: String? = buf.readLine()
//            while (line != null) {
//                val item = Item.fromString(separator, line)
//                item?.let { items.add(item) }
//                line = buf.readLine()
//            }
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
////        items.add(Item(1, 1, "opavdn", false))
////        items.add(Item(2, 1, "qjenta", false))
////        items.add(Item(4, 1, "cvbxopavdn", false))
////        items.add(Item(5, 1, "styyaqjenta", false))
////        items.add(Item(3, 1, "tynhti", true))
////        items.add(Item(6, 1, "hti", true))
////        items.add(Item(7, 2, "opavdn", false))
////        items.add(Item(8, 2, "qjenta", false))
////        items.add(Item(9, 2, "hti", true))
////
////        setItems(items)
//
//        return items
//    }
//
//    fun setItems(items: ArrayList<Item>) {
//        try {
//            val buf = BufferedWriter(FileWriter(file))
//            for (item in items) {
//                buf.write(item.toString(separator))
//                buf.newLine()
//            }
//            buf.close()
//        }  catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    companion object {
//        const val separator = ";"
//    }
//
//}