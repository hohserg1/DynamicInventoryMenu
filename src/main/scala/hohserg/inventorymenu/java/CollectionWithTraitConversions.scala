package hohserg.inventorymenu.java

import hohserg.inventorymenu.java.notify.{MapObservable, CollectionObservable => JavaCollectionObservable}
import hohserg.inventorymenu.notify.{Notified, Observable}

import scala.collection.{Iterator, Traversable}
import collection.JavaConverters._

object CollectionWithTraitConversions {
  def convert[A](jc: JavaCollectionObservable[A]): TraversableOnce[A] with Observable = {
    JTraversableOnceWrapper(jc)
  }
  def convert[K,V](jc: MapObservable[K, V]): TraversableOnce[(K,V)] with Observable = {
    JMapWrapper(jc)
  }

  case class JMapWrapper[K,V](underlying: MapObservable[K, V]) extends TraversableOnce[(K,V)] with Observable {
    override def size: Int = underlying.size()

    override def addNotified(e: Notified): Unit = underlying.addNotified(e)

    override def notifyAllObjects(): Unit = underlying.notifyAllObjects()

    override def foreach[U](f: ((K, V)) => U): Unit = ???

    override def isEmpty: Boolean = ???

    override def hasDefiniteSize: Boolean = ???

    override def seq: TraversableOnce[(K, V)] = underlying.asScala

    override def forall(p: ((K, V)) => Boolean): Boolean = ???

    override def exists(p: ((K, V)) => Boolean): Boolean = ???

    override def find(p: ((K, V)) => Boolean): Option[(K, V)] = ???

    override def copyToArray[B >: (K, V)](xs: Array[B], start: Int, len: Int): Unit = ???

    override def toTraversable: Traversable[(K, V)] = ???

    override def isTraversableAgain: Boolean = ???

    override def toStream: Stream[(K, V)] = ???

    override def toIterator: Iterator[(K, V)] = ???
  }


  case class JTraversableOnceWrapper[A](underlying: JavaCollectionObservable[A]) extends TraversableOnce[A] with Observable {
    override def size: Int = underlying.size()

    override def addNotified(e: Notified): Unit = underlying.addNotified(e)

    override def notifyAllObjects(): Unit = underlying.notifyAllObjects()

    override def foreach[U](f: A => U): Unit = ???

    override def isEmpty: Boolean = ???

    override def hasDefiniteSize: Boolean = ???

    override def seq: TraversableOnce[A] = underlying.asScala

    override def forall(p: A => Boolean): Boolean = ???

    override def exists(p: A => Boolean): Boolean = ???

    override def find(p: A => Boolean): Option[A] = ???

    override def copyToArray[B >: A](xs: Array[B], start: Int, len: Int): Unit = ???

    override def toTraversable: Traversable[A] = ???

    override def isTraversableAgain: Boolean = ???

    override def toStream: Stream[A] = ???

    override def toIterator: Iterator[A] = ???
  }

}
