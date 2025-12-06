using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("humans")]
public class HumansController : ControllerBase
{
    private HumansService humansService;

    public HumansController(HumansService humansService)
    {
        this.humansService = humansService;
    }
    [HttpPost]
    public ActionResult<Human> create ([FromBody] Human human)
    {
        
    }
    [HttpGet]
    public ActionResult<IEnumerable<Human>> getHumans(
        [FromQuery] int from,
        [FromQuery] int pageSize,
        [FromQuery] string filter,
        [FromQuery] string sortBy
    )
    {
        return new ObjectResult(humansService.GetHumans(from, pageSize, filter, sortBy));
    }
}