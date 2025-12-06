using System.Reflection;
using Microsoft.EntityFrameworkCore;

public class HumansContext : DbContext
{
    public DbSet<Human> Humans { get; set; }
    public HumansContext(DbContextOptions<HumansContext> options) : base(options)
    {

    }

}

public class HumansRepository
{
    private readonly HumansContext _context;
    public HumansRepository(HumansContext humansContext)
    {
        _context = humansContext;
    }
    public IEnumerable<Human> FindByLessCoolnessCar(int coolness, int pageNumber, int pageSize)
    {
        return _context.Humans.Where(h => h.Car != null ? h.Car.Coolness < coolness : true)
            .OrderBy(h => h.Id)
            .Skip(pageNumber * pageSize)
            .Take(pageSize);
    }
    public IEnumerable<Human> FindByWeaponType(WeaponType weaponType, int pageNumber, int pageSize)
    {
        return _context.Humans.Where(h => h.WeaponType == weaponType)
            .OrderBy(h => h.Id)
            .Skip(pageNumber * pageSize)
            .Take(pageSize);
    }
    public void DeleteAllByWeaponType(WeaponType weaponType)
    {
        _context.Humans.Where(h => h.WeaponType == weaponType).ExecuteDelete();
    }
    public void DeleteAllByLessCoolCar(int coolness)
    {
        _context.Humans.Where(h => h.Car != null ? h.Car.Coolness < coolness : true).ExecuteDelete();
    }

    public IEnumerable<Human> DynamicQuery<T>(int pageNumber, int pageSize, IEnumerable<(FieldInfo, Func<object?, T, bool>, T)> filters, IEnumerable<FieldInfo> sort)
    {
        return _context.Humans.Where(h => BuildWhere<T>(h, filters));
    }

    private bool BuildWhere<T>(Human human, IEnumerable<(FieldInfo Field, Func<object?, T, bool> Operator, T Value)> filters)
    {
        return filters.All(filter => filter.Operator(filter.Field.GetValue(human), filter.Value));
    }
}
