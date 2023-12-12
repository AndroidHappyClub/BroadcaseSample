package com.github.androidhappyclub.broadcasesample.log

import com.ave.vastgui.tools.log.getLogFactory
import com.ave.vastgui.tools.log.json.GsonConverter
import com.ave.vastgui.tools.log.plugin.LogJson
import com.ave.vastgui.tools.log.plugin.LogPrinter
import com.ave.vastgui.tools.log.plugin.LogSwitch

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2023/12/12
// Description: 
// Documentation:
// Reference:

val mLogFactory = getLogFactory {
    install(LogSwitch) {
        open = true
    }
    install(LogPrinter) {
        maxSingleLogLength = 50
        maxPrintTimes = 3
    }
    install(LogJson) {
        converter = GsonConverter(true)
    }
}