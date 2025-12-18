fun stripAnsi(input: String): String {
    return input.replace(Regex("\u001B\\[[;\\d]*m"), "")
}

class LcrIiTerminal(private val serialPort: UsbSerialPort) {
    fun sendCommand(cmd: String) {
        val data = (cmd + "\r\n").toByteArray()
        serialPort.write(data, 1000)    }
    fun readResponse(): String {
        val buffer = ByteArray(1024)
        val len = serialPort.read(buffer, 2000)
        return String(buffer, 0, len)
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var terminal: LcrIiTerminal
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)

        val usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
        val device = UsbHelper.findSerialDevice(usbManager)


        val adapter = LcrPagerAdapter(this)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Mesures"
                1 -> "Graphes"
                2 -> "Historique"
                else -> "Tab"
            }
        }.attach()
    }
}
