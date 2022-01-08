package com.example.samsungremote

import android.hardware.ConsumerIrManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.samsungremote.SamsungFrequencies.Companion.back
import com.example.samsungremote.SamsungFrequencies.Companion.chDown
import com.example.samsungremote.SamsungFrequencies.Companion.chUp
import com.example.samsungremote.SamsungFrequencies.Companion.down
import com.example.samsungremote.SamsungFrequencies.Companion.enter
import com.example.samsungremote.SamsungFrequencies.Companion.exit
import com.example.samsungremote.SamsungFrequencies.Companion.hex2dec
import com.example.samsungremote.SamsungFrequencies.Companion.left
import com.example.samsungremote.SamsungFrequencies.Companion.menu
import com.example.samsungremote.SamsungFrequencies.Companion.mute
import com.example.samsungremote.SamsungFrequencies.Companion.power
import com.example.samsungremote.SamsungFrequencies.Companion.right
import com.example.samsungremote.SamsungFrequencies.Companion.smartHub
import com.example.samsungremote.SamsungFrequencies.Companion.source
import com.example.samsungremote.SamsungFrequencies.Companion.up
import com.example.samsungremote.SamsungFrequencies.Companion.volumeDown
import com.example.samsungremote.SamsungFrequencies.Companion.volumeUp
import com.example.samsungremote.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private var mCIR: ConsumerIrManager? = null
    private var vibrate: Vibrator? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vibrate = getSystemService(view.context, Vibrator::class.java) as Vibrator
        mCIR = getSystemService(view.context, ConsumerIrManager::class.java) as ConsumerIrManager
        if (mCIR == null || !mCIR!!.hasIrEmitter()) {
            Log.e("mCIR Detection", "No IR Emitter found")
            return
        }

        binding.ButtonPower.setOnClickListener { transmit(hex2dec(power)) }
        binding.ButtonMenu.setOnClickListener { transmit(hex2dec(menu)) }
        binding.ButtonReturn.setOnClickListener { transmit(hex2dec(back)) }
        binding.buttonExit.setOnClickListener { transmit(hex2dec(exit)) }

        binding.buttonVolUp.setOnClickListener { transmit(hex2dec(volumeUp)) }
        binding.buttonVolDown.setOnClickListener { transmit(hex2dec(volumeDown)) }
        binding.ButtonMute.setOnClickListener { transmit(hex2dec(mute)) }

        binding.buttonCHUp.setOnClickListener { transmit(hex2dec(chUp)) }
        binding.buttonCHDown.setOnClickListener { transmit(hex2dec(chDown)) }

        binding.ButtonUp.setOnClickListener { transmit(hex2dec(up)) }
        binding.ButtonDown.setOnClickListener { transmit(hex2dec(down)) }
        binding.ButtonRight.setOnClickListener { transmit(hex2dec(right)) }
        binding.ButtonLeft.setOnClickListener { transmit(hex2dec(left)) }

        binding.ButtonEnter.setOnClickListener { transmit(hex2dec(enter)) }
        binding.SmartHubButton.setOnClickListener { transmit(hex2dec(smartHub)) }
        binding.SourceButton.setOnClickListener { transmit(hex2dec(source)) }
    }

    private fun transmit(irData: MutableList<Int>) {
        vibrate!!.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK))
        try {
            val frequency = irData.removeAt(0)
            mCIR!!.transmit(frequency, irData.toIntArray())
        } catch (e: Exception) {
            Log.e("mCIR Transmit", "ERROR Transmitting data")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}