using System.Text.RegularExpressions;

public class HumansService
{
    private readonly HumansRepository repository;
    public Human Save(Human human)
    {
        return null;
    }
    public Human GetHuman(int id)
    {
        return null;
    }
    public IEnumerable<Human> GetHumans(
        int from,
        int pageSize,
        string filter,
        string sortBy
    )
    {
        Regex regex = new Regex(@"(\\w+.\\w+|\\w+)([<>!=]=?|==)([^;]+)");
        MatchCollection matches = regex.Matches(filter);
        IEnumerable<(FieldInfo, Func<object?, T, bool>, T)> filters;
        foreach (Match match in matches)
        {
            match
        }
    }
    private 
    public Human Update(Human human)
    {
        return null;
    }
    public Human Delete(int id)
    {
        return null;
    }
    public Human DeleteByWeaponType(WeaponType type)
    {
        return null;
    }
    public void DeleteAllByWeaponType(WeaponType type)
    {
    }
    public void DeleteAllByLessCoolCar(int coolness)
    {

    }
}