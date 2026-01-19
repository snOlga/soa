package ejb.service.ejb.i;

import ejb.service.DTO.TeamDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(name = "TeamsService", targetNamespace = "http://i.ejb.service.ejb/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface TeamsService {
    @WebMethod
    @WebResult(name = "team", targetNamespace = "http://i.ejb.service.ejb/")
    public TeamDTO getTeam(@WebParam(name = "id", targetNamespace = "http://i.ejb.service.ejb/") Long id);

    @WebMethod
    @WebResult(name = "team", targetNamespace = "http://i.ejb.service.ejb/")
    public TeamDTO createTeam(
            @WebParam(name = "team", targetNamespace = "http://i.ejb.service.ejb/") TeamDTO dto);

    @WebMethod
    @WebResult(name = "team", targetNamespace = "http://i.ejb.service.ejb/")
    public TeamDTO deleteTeam(@WebParam(name = "id", targetNamespace = "http://i.ejb.service.ejb/") Long id);

    @WebMethod
    @WebResult(name = "team", targetNamespace = "http://i.ejb.service.ejb/")
    public TeamDTO addMember(@WebParam(name = "teamId", targetNamespace = "http://i.ejb.service.ejb/") Long teamId,
            @WebParam(name = "humanId", targetNamespace = "http://i.ejb.service.ejb/") Long humanId);

    @WebMethod
    @WebResult(name = "team", targetNamespace = "http://i.ejb.service.ejb/")
    public TeamDTO removeMemberFromTeam(
            @WebParam(name = "teamId", targetNamespace = "http://i.ejb.service.ejb/") Long teamId,
            @WebParam(name = "humanId", targetNamespace = "http://i.ejb.service.ejb/") Long humanId);

    @WebMethod
    public void deleteMemberByHumanId(
            @WebParam(name = "humanId", targetNamespace = "http://i.ejb.service.ejb/") Long humanId);
}