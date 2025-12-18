class MeasurementsFragment : Fragment(R.layout.fragment_measurements) {
    private lateinit var textView: TextView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textView = view.findViewById(R.id.textViewMeasurements)
        lifecycleScope.launch {
            (activity as MainActivity).viewModel.measurements.collect { measurement ->
                measurement?.let {
                    textView.text = """
Résistance : ${it.resistance}
Inductance : ${it.inductance}
Capacité   : ${it.capacitance}
""".trimIndent()
                }
            }
        }
    }
}
