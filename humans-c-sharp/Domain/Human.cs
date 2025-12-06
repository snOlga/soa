using System.ComponentModel.DataAnnotations;

public class Human
{
    private long id;
    private string name;
    private Coordinates coordinates;
    private DateTime dateTime;
    private bool isRealHero;
    private string? soundTrackName;
    private float impactSpeed;
    private WeaponType? weaponType;
    private Mood mood;
    private Car? car;
    private bool isDeleted = false;
    [Required]
    public long Id
    {
        get { return id; }
        set { id = value; }
    }
    [Required]
    public string Name
    {
        get { return name; }
        set { name = value; }
    }
    [Required]
    public Coordinates Coordinates
    {
        get { return coordinates; }
        set { coordinates = value; }
    }
    [Required]
    public DateTime DateTime
    {
        get { return dateTime; }
        set { dateTime = value; }
    }
    [Required]
    public bool IsRealHero
    {
        get { return isRealHero; }
        set { isRealHero = value; }
    }
    public string? SoundTrackName
    {
        get { return soundTrackName; }
        set { soundTrackName = value; }
    }
    [Required]
    [Range(0, 300)]
    public float ImpactSpeed
    {
        get { return impactSpeed; }
        set { impactSpeed = value; }
    }
    public WeaponType? WeaponType
    {
        get { return weaponType; }
        set { weaponType = value; }
    }
    [Required]
    public Mood Mood
    {
        get { return mood; }
        set { mood = value; }
    }
    public Car? Car
    {
        get { return car; }
        set { car = value; }
    }
    [Required]
    public bool IsDeleted
    {
        get { return isDeleted; }
        set { isDeleted = value; }
    }
}

public class Coordinates
{
    private long id;
    private long x;
    private long y;
    [Required]
    public long Id
    {
        get { return id; }
        set { id = value; }
    }
    [Required]
    [Range(-636, 636)]
    public long X
    {
        get { return x; }
        set { x = value; }
    }
    [Required]
    [Range(-636, 636)]
    public long Y
    {
        get { return y; }
        set { y = value; }
    }
}

public class Car
{
    private long id;
    private string name;
    private long coolness;
    [Required]
    public long Id
    {
        get { return id; }
        set { id = value; }
    }
    [Required]
    public string Name
    {
        get { return name; }
        set { name = value; }
    }
    [Required]
    [Range(0, 100)]
    public long Coolness
    {
        get { return coolness; }
        set { coolness = value; }
    }
}

public enum WeaponType
{
    AXE,
    KNIFE,
    MACHINE_GUN,
    BAT
}

public enum Mood
{
    SORROW,
    LONGING,
    GLOOM,
    APATHY,
    FRENZY
}

