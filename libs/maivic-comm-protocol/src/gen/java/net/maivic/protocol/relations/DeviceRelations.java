//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'devices'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.protocol.Model.Device;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.comm.Table;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Client;
import net.maivic.comm.Callback;
@Table("devices")
public interface DeviceRelations {
  @Relation(name="clients",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Client>>getClients(Device  o , Callback<Client> cb);
};

