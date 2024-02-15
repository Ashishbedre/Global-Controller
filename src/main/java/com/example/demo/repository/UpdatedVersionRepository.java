//package com.example.demo.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//
//import com.example.demo.model.UpdateVersionModel;
//
//import jakarta.transaction.Transactional;
//
//public interface UpdatedVersionRepository extends JpaRepository<UpdateVersionModel, Long>{
//
////	@Query(value="SELECT m.version from updated_version_data m WHERE m.deployment_id=?1 AND m.product_name=?2",nativeQuery = true)
////	public List<String> getListOfVersion(String deploymentId,String productName);
//
//
//	@Modifying(clearAutomatically = true)
//	@Transactional
//	@Query(value="DELETE m from updated_version_data m WHERE m.deployment_id=?1",nativeQuery = true)
//	public void deleteData(String deploymentId);
//}
