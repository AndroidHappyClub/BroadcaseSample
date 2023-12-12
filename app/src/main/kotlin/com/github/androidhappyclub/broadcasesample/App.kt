package com.github.androidhappyclub.broadcasesample

import android.app.Application
import com.ave.vastgui.tools.config.ToolsConfig

// Author: Vast Gui
// Email: guihy2019@gmail.com
// Date: 2023/12/11
// Description: 
// Documentation:
// Reference:

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        ToolsConfig.init(this)
    }

}