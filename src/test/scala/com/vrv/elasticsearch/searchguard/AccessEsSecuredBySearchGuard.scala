package com.vrv.elasticsearch.searchguard
import java.net.InetAddress

import com.floragunn.searchguard.SearchGuardPlugin
import com.floragunn.searchguard.ssl.SearchGuardSSLPlugin
import com.floragunn.searchguard.ssl.util.SSLConfigConstants
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.junit._

class AccessEsSecuredBySearchGuard {

  @Test
  def accessEs(): Unit ={
    // Setting
    val settings:Settings = Settings.settingsBuilder()
      .put("cluster.name", "vrv_es").build();
    // Client
    val client:TransportClient = TransportClient.builder().settings(settings).build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.2.16"), 9300))
    // ClusterHealthResponse
    val healths:ClusterHealthResponse = client.admin().cluster().prepareHealth().get()
    println(s"ClusterName：${healths.getClusterName()}")
    println(s"NumberOfDataNodes：${healths.getNumberOfDataNodes()}")
    println(s"NumberOfNodes：${healths.getNumberOfNodes()}")
    client.close
  }

  @Test
  def accessEsSecuredBySearchGuard(): Unit ={
    // Setting
//    val settings:Settings = Settings.settingsBuilder()
//      .put("cluster.name", "elasticsearch")
//      .put(SSLConfigConstants.SEARCHGUARD_SSL_TRANSPORT_ENFORCE_HOSTNAME_VERIFICATION, false)
//      .build()
//    // Client
//    val tc =  new PreBuiltTransportClient(settings, classOf[SearchGuardPlugin])
//
//    val client:TransportClient = TransportClient.builder().settings(settings).addPlugin(classOf[SearchGuardSSLPlugin]).build()
//      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.2.141"), 9300))
//
//    client.threadPool().getThreadContext().putHeader("Authorization", "Basic "+encodeBase64("username:password"))
//    // ClusterHealthResponse
//    val healths:ClusterHealthResponse = client.admin().cluster().prepareHealth().get()
//    println(s"ClusterName：${healths.getClusterName()}")
//    println(s"NumberOfDataNodes：${healths.getNumberOfDataNodes()}")
//    println(s"NumberOfNodes：${healths.getNumberOfNodes()}")
//    client.close
  }

}
