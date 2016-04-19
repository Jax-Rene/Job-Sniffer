package com.zhuangjy.util

import java.io.{BufferedReader, InputStream, InputStreamReader, LineNumberReader}

/**
  * Created by johnny on 16/4/19.
  */
object ShellUtil {
  def isLinux: Boolean = {
    if (System.getProperty("os.name").toUpperCase.indexOf("WINDOWS") == -1) {
      return true
    }
    return false
  }

  def runShell(command: String): Int = {
    if (!isLinux) {
      return -1
    }
    var p: Process = null
    var is: InputStreamReader = null
    var input: LineNumberReader = null
    var es: InputStreamReader = null
    var error: LineNumberReader = null
    try {
      p = Runtime.getRuntime.exec(Array[String]("/bin/sh", "-c", command))
      is = new InputStreamReader(p.getInputStream)
      input = new LineNumberReader(is)
      es = new InputStreamReader(p.getErrorStream)
      error = new LineNumberReader(es)
      p.waitFor
      return p.exitValue
    }
    catch {
      case e: Exception => {
        return -1
      }
    } finally {
      try {
        if (input != null) input.close
        if (is != null) is.close
        if (p != null) p.destroy
      }
      catch {
        case e: Exception => {
        }
      }
    }
  }

  def runShell(command: Array[String]): Int = {
    if (!isLinux) {
      return -1
    }
    var p: Process = null
    var is: InputStreamReader = null
    var input: LineNumberReader = null
    var es: InputStreamReader = null
    var error: LineNumberReader = null
    try {
      p = Runtime.getRuntime.exec(command)
      is = new InputStreamReader(p.getInputStream)
      input = new LineNumberReader(is)
      es = new InputStreamReader(p.getErrorStream)
      error = new LineNumberReader(es)
      p.waitFor
      return p.exitValue
    }
    catch {
      case e: Exception => {
        return -1
      }
    } finally {
      try {
        if (input != null) input.close
        if (is != null) is.close
        if (p != null) p.destroy
      }
      catch {
        case e: Exception => {
        }
      }
    }
  }

  def runShell4Result(cmd: String): String = {
    if (!isLinux) {
      return ""
    }
    var result: String = ""
    var p: Process = null
    var in: BufferedReader = null
    var isr: InputStreamReader = null
    var is: InputStream = null
    try {
      p = Runtime.getRuntime.exec(Array[String]("/bin/sh", "-c", cmd))
      is = p.getInputStream
      isr = new InputStreamReader(is)
      in = new BufferedReader(isr)
      var line: String = in.readLine
      while (line != null) {
        {
          result += line + "\n"
          line = in.readLine
        }
      }
      p.waitFor
      result
    }
    catch {
      case e: Exception => {
        result
      }
    } finally {
      try {
        if (is != null) is.close
        if (isr != null) isr.close
        if (in != null) in.close
        if (p != null) p.destroy
        result
      }
      catch {
        case e: Exception => {
          result
        }
      }
    }
  }
}
