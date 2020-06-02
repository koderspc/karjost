package com.example.karjost

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.GridView
import com.example.karjost.Model.Category
import kotlinx.android.synthetic.main.category_entry.view.*

class JobsCategories : AppCompatActivity() {


    var adapter: CategoryAdapter? = null
    var categoriesList = ArrayList<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        var category_gv = findViewById<GridView>(R.id.category_gv)
        category_gv.choiceMode = GridView.CHOICE_MODE_MULTIPLE_MODAL

        categoriesList = ArrayList()
        categoriesList.add(Category("اداری و مالی", R.drawable.image))
        categoriesList.add(Category("آموزشی،فرهنگی و هنری", R.drawable.image))
        categoriesList.add(Category("بهداشتی و درمانی", R.drawable.image))
        categoriesList.add(Category("امور اجتماعی", R.drawable.image))
        categoriesList.add(Category("کشاورزی و محیط زیست", R.drawable.image))
        categoriesList.add(Category("خدمات", R.drawable.image))
        categoriesList.add(Category("فرآوری داده ها", R.drawable.image))
        categoriesList.add(Category("فنی و مهندسی ", R.drawable.image))

        adapter = CategoryAdapter(this)
        adapter!!.setList(categoriesList)

        category_gv.adapter = adapter







    }


    class CategoryAdapter(context: Context) : BaseAdapter() {

        var categoriesList = ArrayList<Category>()
        var context : Context? = context

        init {
            this.context = context
            this.categoriesList = ArrayList()
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val category = categoriesList[position]
            var inflator = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var categoryView = inflator.inflate(R.layout.category_entry,parent, false)
            categoryView.title_tv.text = category.title
            categoryView.image_iv.setImageResource(category.imageUrl!!)

            return categoryView
        }

        override fun getItem(position: Int): Any {

            return categoriesList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return categoriesList.size
        }

        fun setList(  categoriesList: ArrayList<Category>){
            this.categoriesList = ArrayList()
            this.categoriesList = categoriesList
            notifyDataSetChanged()
        }
    }


}
