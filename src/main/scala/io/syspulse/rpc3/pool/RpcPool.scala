package io.syspulse.rpc3.pool

import scala.util.Try
import scala.util.{Success,Failure}
import scala.collection.immutable

import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors

import com.typesafe.scalalogging.Logger

import io.jvm.uuid._
import scala.concurrent.Future

import io.syspulse.rpc3.Config
import io.syspulse.rpc3.cache.ProxyCache

// --- Session -------------------------------------------------------------------------------
abstract class RpcSession(pool:Seq[String]) {
  val id = util.Random.nextLong()
  // called when rpc failed
  def failed():String
  // called to get next rpc connection
  def next():String

  def available:Boolean  
  def retry:Int
  def lap:Int
  
}

// --- Pool -------------------------------------------------------------------------------
trait RpcPool {
  def pool():Seq[String] 
  def connect(req:String):RpcSession

  override def toString() = s"${this.getClass()}(${pool()})"
}
