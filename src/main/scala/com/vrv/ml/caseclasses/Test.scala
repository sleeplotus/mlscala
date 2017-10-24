package com.vrv.ml.caseclasses

object Test extends App {
  val frankenstein = Book("978-0486282114")
  println(frankenstein.isbn)

  val message1 = Message("guillaume@quebec.ca", "jorge@catalonia.es", "Ça va ?")
  println(message1.sender)

  val message2 = Message("jorge@catalonia.es", "guillaume@quebec.ca", "Com va?")
  val message3 = Message("jorge@catalonia.es", "guillaume@quebec.ca", "Com va?")
  val messagesAreTheSame = message2 == message3
  println(messagesAreTheSame )

  val message4 = Message("julien@bretagne.fr", "travis@washington.us", "Me zo o komz gant ma amezeg")
  val message5 = message4.copy(sender = message4.recipient, recipient = "claire@bourgogne.fr")
  message5.sender  // travis@washington.us
  message5.recipient // claire@bourgogne.fr
  println(message5.recipient)
  message5.body  // "Me zo o komz gant ma amezeg"
}
