package nl.kampmeijer.adthemovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.FragmentManager
import nl.kampmeijer.adthemovies.model.Date
import nl.kampmeijer.adthemovies.model.Movie

class MovieListFragment : Fragment() {
    val movies = ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_movie_list, container, false)

        val lv: ListView
        lv = v.findViewById<ListView>(R.id.lvFilms)
        fillMovies()
        val adapter : ArrayAdapter<Movie>
        adapter = ArrayAdapter(v.context, android.R.layout.simple_list_item_1, movies)
        lv.adapter = adapter

        lv.setOnItemClickListener{
                parent, view, position, id ->
            val m = parent.getItemAtPosition(position)
            val fm : FragmentManager
            fm = parentFragmentManager
            val fragDetails = fm.findFragmentById(R.id.fcvDetails) as MovieDetailsFragment
            fragDetails.fillInfo(m as Movie)
        }

        return v
    }

    fun fillMovies(){
        val m1 = Movie(1, "Once upon a time in Hollywood", "", 4, "Komisch drama", Date(1, 10, 2019))
        val m2 = Movie(2, "Reservoir Dogs", "", 4, "Misdaad", Date(1, 6, 1992))
        val m3 = Movie(3, "Pulp Fiction", "", 4, "Komisch drama", Date(1, 6, 1994))
        movies.add(m1)
        movies.add(m2)
        movies.add(m3)
    }
}