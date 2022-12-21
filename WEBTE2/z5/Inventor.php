<?php

require_once "MyPDO.php";

class Inventor
{
    /* @var MyPDO */
    protected $db;

    protected int $id;
    protected string $name;
    protected string $surname;
    protected DateTime $birth_date;
    protected string $birth_place;
    protected DateTime $death_date;
    protected string $death_place;
    protected string $description;

    public function __construct()
    {
        $this->db = MyPDO::instance();
    }

    /**
     * @return int
     */
    public function getId(): int
    {
        return $this->id;
    }

    /**
     * @return string
     */
    public function getName(): string
    {
        return $this->name;
    }

    /**
     * @param string $name
     */
    public function setName(string $name): void
    {
        $this->name = $name;
    }

    /**
     * @return string
     */
    public function getSurname(): string
    {
        return $this->surname;
    }

    /**
     * @param string $surname
     */
    public function setSurname(string $surname): void
    {
        $this->surname = $surname;
    }

    /**
     * @return DateTime
     */
    public function getBirthDate(): DateTime
    {
        return $this->birth_date;
    }

    /**
     * @param string $birth_date
     */
    public function setBirthDate(string $birth_date): void
    {
        $this->birth_date = DateTime::createFromFormat('d.m.Y', $birth_date);
    }

    /**
     * @return string
     */
    public function getBirthPlace(): string
    {
        return $this->birth_place;
    }

    /**
     * @param string $birth_place
     */
    public function setBirthPlace(string $birth_place): void
    {
        $this->birth_place = $birth_place;
    }

    /**
     * @return DateTime
     */
    public function getDeathDate(): DateTime
    {
        return $this->death_date;
    }

    /**
     * @param string $death_date
     */
    public function setDeathDate(string $death_date): void
    {
        $this->death_date = DateTime::createFromFormat('d.m.Y', $death_date);
    }

    /**
     * @return string
     */
    public function getDeathPlace(): string
    {
        return $this->death_place;
    }

    /**
     * @param string $death_place
     */
    public function setDeathPlace(string $death_place): void
    {
        $this->death_place = $death_place;
    }

    /**
     * @return string
     */
    public function getDescription(): string
    {
        return $this->description;
    }

    /**
     * @param string $description
     */
    public function setDescription(string $description): void
    {
        $this->description = $description;
    }

    public static function all()
    {
        return MyPDO::instance()->run("SELECT * FROM inventors")->fetchAll();
    }

    public static function findById($id)
    {
        return MyPDO::instance()->run("SELECT * FROM inventors WHERE id = ?", [$id])->fetch();
    }

    public static function findByIdWithInventios($id)
    {
        $inventor = MyPDO::instance()->run("SELECT * FROM inventors WHERE id = ?", [$id])->fetch();
        if($inventor)
        {
            $inventor = json_decode(json_encode($inventor), TRUE);
            $inventions = MyPDO::instance()->run("SELECT description FROM inventions WHERE inventor_id = ?", [$inventor['id']])->fetchAll();
            $inventor['inventions'] = $inventions;
        }
        return $inventor;
    }

    public static function findBySurname($surname)
    {
        return MyPDO::instance()->run("SELECT * FROM inventors WHERE surname = ?", [$surname])->fetch();
    }

    public static function findByBirthYear($year)
    {
        return MyPDO::instance()->run("SELECT name, surname, birth_date FROM inventors WHERE year(birth_date) = ?;", [$year])->fetchAll();
    }

    public static function findByDeathYear($year)
    {
        return MyPDO::instance()->run("SELECT name, surname, death_date FROM inventors WHERE year(death_date) = ?;", [$year])->fetchAll();
    }

    public function save()
    {
        $this->db->run("INSERT into inventors
            (`name`, `surname`, `birth_date`, `birth_place`, `description`, `death_date`, `death_place` ) values (?, ?, ?, ?, ?, ?, ?)",
            [$this->name, $this->surname, $this->birth_date->format('Y.m.d'), $this->birth_place, $this->description,
                isset($this->death_date) ? $this->death_date->format('Y.m.d') : null, $this->death_place ?? null]);
        $this->id = $this->db->lastInsertId();
    }

    public function update($id)
    {
        $formated_bd = $this->birth_date->format('Y.m.d');
        $formated_dd = $this->death_date->format('Y.m.d');
        $this->db->run("UPDATE inventors SET
            `name`='$this->name', 
            `surname`='$this->surname', 
            `birth_date`='$formated_bd', 
            `birth_place`='$this->birth_place', 
            `description`='$this->description',
            `death_date`='$formated_dd',
            `death_place`='$this->death_place'
            WHERE `id`='$id'");
        $this->id = $id;
    }

    public static function destroy($id)
    {
        MyPDO::instance()->run("DELETE from inventors WHERE id = ?", [$id]);
    }

    public function toArray()
    {
        return ["id" => $this->id,
                "name" => $this->name,
                "surname" => $this->surname,
                "birth_date" => $this->birth_date->format('d.m.Y'),
                "birth_place" => $this->birth_place,
                "death_date" => $this->death_date->format('d.m.Y'),
                "death_place" => $this->death_place,
                "description" => $this->description];
    }
}