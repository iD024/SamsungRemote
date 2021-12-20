package com.example.samsungremote

import android.hardware.ConsumerIrManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.samsungremote.SamsungFrequencys.Companion.chDown
import com.example.samsungremote.SamsungFrequencys.Companion.chUp
import com.example.samsungremote.SamsungFrequencys.Companion.down
import com.example.samsungremote.SamsungFrequencys.Companion.enter
import com.example.samsungremote.SamsungFrequencys.Companion.hex2dec
import com.example.samsungremote.SamsungFrequencys.Companion.left
import com.example.samsungremote.SamsungFrequencys.Companion.menu
import com.example.samsungremote.SamsungFrequencys.Companion.mute
import com.example.samsungremote.SamsungFrequencys.Companion.power
import com.example.samsungremote.SamsungFrequencys.Companion.right
import com.example.samsungremote.SamsungFrequencys.Companion.smartHub
import com.example.samsungremote.SamsungFrequencys.Companion.up
import com.example.samsungremote.SamsungFrequencys.Companion.volumeDown
import com.example.samsungremote.SamsungFrequencys.Companion.volumeUp
import com.example.samsungremote.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    var mCIR: ConsumerIrManager? = null


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
        mCIR = getSystemService(view.context, ConsumerIrManager::class.java) as ConsumerIrManager
        if (mCIR == null || !mCIR!!.hasIrEmitter()) {
            Log.e("mCIR Detection", "No IR Emitter found");
            return
        }

        binding.ButtonPower.setOnClickListener { mCIR!!.transmit(38028, hex2dec(power)) }
        binding.ButtonMenu.setOnClickListener { mCIR!!.transmit(38028, hex2dec(menu)) }

        binding.buttonVolUp.setOnClickListener { mCIR!!.transmit(38028, hex2dec(volumeUp)) }
        binding.buttonVolDown.setOnClickListener { mCIR!!.transmit(38028, hex2dec(volumeDown)) }
        binding.ButtonMute.setOnClickListener { mCIR!!.transmit(38028, hex2dec(mute)) }

        binding.buttonCHUp.setOnClickListener { mCIR!!.transmit(38028, hex2dec(chUp)) }
        binding.buttonCHDown.setOnClickListener { mCIR!!.transmit(38028, hex2dec(chDown)) }

        binding.ButtonUp.setOnClickListener { mCIR!!.transmit(38028, hex2dec(up)) }
        binding.ButtonDown.setOnClickListener { mCIR!!.transmit(38028, hex2dec(down)) }
        binding.ButtonRight.setOnClickListener { mCIR!!.transmit(38028, hex2dec(right)) }
        binding.ButtonLeft.setOnClickListener { mCIR!!.transmit(38028, hex2dec(left)) }

        binding.ButtonEnter.setOnClickListener { mCIR!!.transmit(38028, hex2dec(enter)) }
        binding.SmartHubButton.setOnClickListener { mCIR!!.transmit(38028, hex2dec(smartHub)) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}