package nl.kampmeijer.adthemovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import nl.kampmeijer.adthemovies.model.Movie

class MovieDetailsFragment : Fragment() {
    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_movie_details, container, false)
        return v
    }

    fun fillInfo(m : Movie){
        val tvTitle : TextView = v.findViewById(R.id.tvTitel)
        val tvGenre : TextView = v.findViewById(R.id.tvCategorie)
        val rating : RatingBar = v.findViewById(R.id.ratingBar)
        tvTitle.text = m.title
        tvGenre.text = m.category
        rating.rating = m.rating.toFloat()
    }
}