package com.example.fitnessapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import pl.droidsonroids.gif.GifImageView

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment : Fragment() {

    private val exercise : MutableList<Exercise> = mutableListOf(
        Exercise(exerciseType = "gifone", exerciseCount = 8),
        Exercise(exerciseType = "giftwo", exerciseCount = 5),
        Exercise(exerciseType = "gifthree", exerciseCount = 10),
        Exercise(exerciseType = "giffour", exerciseCount = 15),
        Exercise(exerciseType = "giffive", exerciseCount = 20)
    )

    lateinit var nextButton: Button
    lateinit var exitButton: Button
    lateinit var imageView: GifImageView
    lateinit var textView: TextView

    private lateinit var currentExercise: Exercise
    private var count: Int = 0
    private var exerciseIndex: Int = 0
    private var exerciseSize = ((exercise.size + 1) / 2).coerceAtMost(3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        nextButton = v.findViewById(R.id.btn_2)
        exitButton = v.findViewById(R.id.btn_1)
        imageView = v.findViewById(R.id.img_gif)
        textView = v.findViewById(R.id.txt_vw)

        randomizeExercise()

        nextButton.setOnClickListener{
            view:View->
            exerciseIndex++

            if(exerciseIndex<exerciseSize){
                currentExercise = exercise[exerciseIndex]
                setExercise()
            }
            else{
                view.findNavController().navigate(R.id.action_exerciseFragment_to_wellDoneFragment)
            }
        }

        exitButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_exerciseFragment_to_tryAgainFragment))


    }

    fun randomizeExercise(){
        exercise.shuffle()
        exerciseIndex = 0
        setExercise()
    }

    private fun setExercise(){

        currentExercise = exercise[exerciseIndex]
        count = currentExercise.exerciseCount
        textView.text = String.format(getString(R.string.exercise_text_view), count)
        imageView.setImageResource(resources.getIdentifier(currentExercise.exerciseType,"drawable"
            , (activity as AppCompatActivity).packageName))

        (activity as AppCompatActivity).supportActionBar?.title = String.format(getString(R.string.title_fitness_exercise),exerciseIndex + 1, exerciseSize)

    }

}